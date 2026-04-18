package com.andresdevs.restaurant.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresdevs.restaurant.core.constants.FirebaseCollections
import com.andresdevs.restaurant.domain.model.UserRole
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    init {
        checkSession()
    }

    private fun checkSession() {
        val currentUser = auth.currentUser ?: return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val role = getOrCreateRole(currentUser.uid, currentUser.email.orEmpty())
            _state.update {
                it.copy(
                    isLoading = false,
                    isAuthenticated = true,
                    role = role
                )
            }
        }
    }

    fun login(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _state.update { it.copy(error = "Campos obligatorios") }
            return
        }
        
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, message = null) }
            auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener { authResult ->
                    viewModelScope.launch {
                        val uid = authResult.user?.uid.orEmpty()
                        val userEmail = authResult.user?.email.orEmpty()
                        val role = getOrCreateRole(uid, userEmail)
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isAuthenticated = true,
                                isSuccess = true,
                                error = null,
                                message = null,
                                role = role
                            )
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message,
                            message = null
                        )
                    }
                }
        }
    }

    fun register(email: String, pass: String) {
        if (email.isBlank() || pass.length < 6) {
            _state.update { it.copy(error = "Correo y contraseña (min 6) son obligatorios") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, message = null) }
            auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener { authResult ->
                    viewModelScope.launch {
                        val uid = authResult.user?.uid.orEmpty()
                        upsertRoleDocument(uid, email, UserRole.MESERO)
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isAuthenticated = true,
                                isSuccess = true,
                                message = "Registro exitoso",
                                role = UserRole.MESERO
                            )
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message,
                            message = null
                        )
                    }
                }
        }
    }

    fun resetPassword(email: String) {
        if (email.isBlank()) {
            _state.update { it.copy(error = "Debes ingresar un correo válido") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, message = null) }
            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            message = "Se envio correo de recuperacion"
                        )
                    }
                }
                .addOnFailureListener { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message
                        )
                    }
                }
        }
    }
    
    fun logout() {
        auth.signOut()
        _state.update {
            it.copy(
                isAuthenticated = false,
                isSuccess = false,
                message = null,
                role = null
            )
        }
    }

    fun clearFeedback() {
        _state.update { it.copy(error = null, message = null) }
    }

    private suspend fun getOrCreateRole(uid: String, email: String): UserRole {
        if (uid.isBlank()) return UserRole.CLIENTE

        val byUid = firestore.collection(FirebaseCollections.USERS).document(uid).get().await()
        if (byUid.exists()) {
            val roleValue = byUid.getString("cargo")
            val parsedRole = parseRole(roleValue)
            if (roleValue != parsedRole.name) {
                upsertRoleDocument(uid, email, parsedRole)
            }
            return parsedRole
        }

        if (email.isNotBlank()) {
            val byEmail = firestore.collection(FirebaseCollections.USERS)
                .whereEqualTo("correo", email)
                .limit(1)
                .get()
                .await()
            val first = byEmail.documents.firstOrNull()
            if (first != null) {
                val role = parseRole(first.getString("cargo"))
                upsertRoleDocument(uid, email, role)
                return role
            }
        }

        upsertRoleDocument(uid, email, UserRole.MESERO)
        return UserRole.MESERO
    }

    private suspend fun upsertRoleDocument(uid: String, email: String, role: UserRole) {
        if (uid.isBlank()) return
        val payload = hashMapOf(
            "uid" to uid,
            "correo" to email,
            "cargo" to role.name
        )
        firestore.collection(FirebaseCollections.USERS)
            .document(uid)
            .set(payload)
            .await()
    }

    private fun parseRole(raw: String?): UserRole {
        return when (raw?.trim()?.uppercase()) {
            "ADMIN", "ADMINISTRADOR" -> UserRole.ADMIN
            "MESERO" -> UserRole.MESERO
            "CAJERO" -> UserRole.CAJERO
            "COCINA" -> UserRole.COCINA
            else -> UserRole.CLIENTE
        }
    }
}
