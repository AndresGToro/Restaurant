package com.andresdevs.restaurant.presentation.categoria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresdevs.restaurant.domain.model.Categoria
import com.andresdevs.restaurant.domain.usecase.categoria.CreateCategoriaUseCase
import com.andresdevs.restaurant.domain.usecase.categoria.DeleteCategoriaUseCase
import com.andresdevs.restaurant.domain.usecase.categoria.GetCategoriaUseCase
import com.andresdevs.restaurant.domain.usecase.categoria.UpdateCategoriaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriaViewModel(
    private val getCategoriasUseCase: GetCategoriaUseCase,
    private val createCategoriaUseCase: CreateCategoriaUseCase,
    private val updateCategoriaUseCase: UpdateCategoriaUseCase,
    private val deleteCategoriaUseCase: DeleteCategoriaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CategoriaState())
    val state: StateFlow<CategoriaState> = _state.asStateFlow()

    init {
        cargarCategorias()
    }

    fun cargarCategorias() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val categorias = getCategoriasUseCase()
                _state.update { it.copy(categorias = categorias, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun onNombreChange(nuevoNombre: String) {
        _state.update { it.copy(nombre = nuevoNombre) }
    }

    fun onUrlChange(nuevaUrl: String) {
        _state.update { it.copy(url = nuevaUrl) }
    }

    fun guardarCategoria() {
        val currentNombre = _state.value.nombre
        val currentUrl = _state.value.url
        
        if (currentNombre.isBlank() || currentUrl.isBlank()) {
            _state.update { it.copy(error = "El nombre y la URL son obligatorios") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val nueva = Categoria(
                codeCategoria = "",
                nombre = currentNombre,
                imagenUrl = currentUrl,
                estado = "Activo"
            )
            val success = createCategoriaUseCase(nueva)
            if (success) {
                _state.update { it.copy(isLoading = false, isSuccess = true, nombre = "", url = "") }
                cargarCategorias()
            } else {
                _state.update { it.copy(isLoading = false, error = "Error al guardar la categoría") }
            }
        }
    }

    fun eliminarCategoria(categoria: Categoria) {
        viewModelScope.launch {
            val success = deleteCategoriaUseCase(categoria.codeCategoria)
            if (success) {
                cargarCategorias()
            } else {
                _state.update { it.copy(error = "Error al eliminar la categoría") }
            }
        }
    }

    fun actualizarCategoria(categoria: Categoria, nuevoNombre: String, nuevaUrl: String) {
        if (nuevoNombre.isBlank() || nuevaUrl.isBlank()) {
            _state.update { it.copy(error = "El nombre y la URL son obligatorios") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val updated = categoria.copy(
                nombre = nuevoNombre,
                imagenUrl = nuevaUrl
            )
            val success = updateCategoriaUseCase(updated)
            if (success) {
                _state.update { it.copy(isLoading = false, isSuccess = true) }
                cargarCategorias()
            } else {
                _state.update { it.copy(isLoading = false, error = "Error al actualizar la categoria") }
            }
        }
    }
    
    fun resetSuccess() {
        _state.update { it.copy(isSuccess = false) }
    }
}
