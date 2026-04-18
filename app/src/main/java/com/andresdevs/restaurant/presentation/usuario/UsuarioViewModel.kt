package com.andresdevs.restaurant.presentation.usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresdevs.restaurant.domain.model.UserRole
import com.andresdevs.restaurant.domain.model.Usuario
import com.andresdevs.restaurant.domain.usecase.usuario.CreateUsuarioUseCase
import com.andresdevs.restaurant.domain.usecase.usuario.DeleteUsuarioUseCase
import com.andresdevs.restaurant.domain.usecase.usuario.GetUsuarioUseCase
import com.andresdevs.restaurant.domain.usecase.usuario.UpdateUsuarioUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val getUsuarioUseCase: GetUsuarioUseCase,
    private val createUsuarioUseCase: CreateUsuarioUseCase,
    private val updateUsuarioUseCase: UpdateUsuarioUseCase,
    private val deleteUsuarioUseCase: DeleteUsuarioUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UsuarioState())
    val state: StateFlow<UsuarioState> = _state.asStateFlow()

    init {
        cargarUsuarios()
    }

    fun cargarUsuarios() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val usuarios = getUsuarioUseCase()
                _state.update { it.copy(isLoading = false, usuarios = usuarios) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun onCedulaChange(value: String) { _state.update { it.copy(cedula = value) } }
    fun onNombreCompletoChange(value: String) { _state.update { it.copy(nombreCompleto = value) } }
    fun onUsuarioChange(value: String) { _state.update { it.copy(usuario = value) } }
    fun onCargoChange(value: String) { _state.update { it.copy(cargo = value) } }
    fun onCelularChange(value: String) { _state.update { it.copy(celular = value) } }
    fun onCorreoChange(value: String) { _state.update { it.copy(correo = value) } }
    fun onContrasenaChange(value: String) { _state.update { it.copy(contrasena = value) } }
    fun onDireccionChange(value: String) { _state.update { it.copy(direccion = value) } }

    fun guardarUsuario() {
        val current = _state.value
        if (current.cedula.isBlank() || current.nombreCompleto.isBlank() || current.usuario.isBlank()) {
            _state.update { it.copy(error = "Cedula, nombre y usuario son obligatorios") }
            return
        }
        val normalizedRole = normalizeRole(current.cargo)

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val usuario = Usuario(
                cedula = current.cedula,
                nombreCompleto = current.nombreCompleto,
                usuario = current.usuario,
                cargo = normalizedRole,
                celular = current.celular,
                correo = current.correo,
                contrasena = current.contrasena,
                direccion = current.direccion
            )
            val success = createUsuarioUseCase(usuario)
            if (success) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true,
                        cedula = "",
                        nombreCompleto = "",
                        usuario = "",
                        cargo = "",
                        celular = "",
                        correo = "",
                        contrasena = "",
                        direccion = ""
                    )
                }
                cargarUsuarios()
            } else {
                _state.update { it.copy(isLoading = false, error = "No se pudo guardar el usuario") }
            }
        }
    }

    fun eliminarUsuario(cedula: String) {
        viewModelScope.launch {
            val success = deleteUsuarioUseCase(cedula)
            if (success) {
                cargarUsuarios()
            } else {
                _state.update { it.copy(error = "No se pudo eliminar el usuario") }
            }
        }
    }

    private fun normalizeRole(value: String): String {
        return when (value.trim().uppercase()) {
            "ADMIN", "ADMINISTRADOR" -> UserRole.ADMIN.name
            "MESERO" -> UserRole.MESERO.name
            "CAJERO" -> UserRole.CAJERO.name
            "COCINA" -> UserRole.COCINA.name
            else -> UserRole.CLIENTE.name
        }
    }
}
