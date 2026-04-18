package com.andresdevs.restaurant.data.firebase

import com.andresdevs.restaurant.core.constants.FirebaseCollections
import com.andresdevs.restaurant.data.model.UsuarioDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsuarioFirebaseService @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val collection = firestore.collection(FirebaseCollections.USERS)

    suspend fun getAllUsuarios(): List<UsuarioDto> {
        val snapshot = collection.get().await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(UsuarioDto::class.java)?.copy(
                cedula = if (doc.id.isNotBlank()) doc.id else ""
            )
        }
    }

    suspend fun createUsuario(usuario: UsuarioDto): Boolean {
        if (usuario.cedula.isBlank()) return false
        return try {
            collection.document(usuario.cedula).set(usuario).await()
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun updateUsuario(usuario: UsuarioDto): Boolean {
        if (usuario.cedula.isBlank()) return false
        return try {
            collection.document(usuario.cedula).set(usuario).await()
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun deleteUsuario(usuarioId: String): Boolean {
        if (usuarioId.isBlank()) return false
        return try {
            collection.document(usuarioId).delete().await()
            true
        } catch (_: Exception) {
            false
        }
    }
}
