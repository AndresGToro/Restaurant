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
import kotlinx.coroutines.launch

class CategoriaViewModel(
    private val getCategoriasUseCase: GetCategoriaUseCase,
    private val createCategoriaUseCase: CreateCategoriaUseCase,
    private val updateCategoriaUseCase: UpdateCategoriaUseCase,
    private val deleteCategoriaUseCase: DeleteCategoriaUseCase
) : ViewModel() {

    // 🔄 Estado UI
    var _state = MutableStateFlow(CategoriaState())
    val state: StateFlow<CategoriaState> = _state

    init {
        cargarCategorias()
    }

    private fun cargarCategorias() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val categorias = getCategoriasUseCase()
            _state.value = _state.value.copy(
                categorias = categorias,
                isLoading = false
            )
        }
    }

    fun onNombreChange(nuevoNombre: String) {
        _state.value = _state.value.copy(nombre = nuevoNombre)
    }

    fun onUrlChange(nuevaUrl: String) {
        _state.value = _state.value.copy(url = nuevaUrl)
    }

    fun onGuardarClick() {
        viewModelScope.launch {
            val nueva = Categoria(
                codeCategoria = generateId(), // usar UUID.randomUUID().toString() o key de Firebase
                nombre = state.value.nombre,
                imagenUrl = state.value.url,
                estado = "Activo"
            )
            createCategoriaUseCase(nueva)
            cargarCategorias() // recargar lista
            _state.value = _state.value.copy(nombre = "", url = "")
        }
    }

    private fun generateId(): String {
        // Aquí podés usar Firebase push().key si querés generar desde backend
        return System.currentTimeMillis().toString()
    }
}