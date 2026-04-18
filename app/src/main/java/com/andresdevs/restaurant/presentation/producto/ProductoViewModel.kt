package com.andresdevs.restaurant.presentation.producto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresdevs.restaurant.domain.model.Producto
import com.andresdevs.restaurant.domain.usecase.producto.CreateProductoUseCase
import com.andresdevs.restaurant.domain.usecase.producto.DeleteProductoUseCase
import com.andresdevs.restaurant.domain.usecase.producto.GetProductoUseCase
import com.andresdevs.restaurant.domain.usecase.producto.UpdateProductoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductoViewModel(
    private val getProductosUseCase: GetProductoUseCase,
    private val createProductoUseCase: CreateProductoUseCase,
    private val updateProductoUseCase: UpdateProductoUseCase,
    private val deleteProductoUseCase: DeleteProductoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProductoState())
    val state: StateFlow<ProductoState> = _state.asStateFlow()

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val productos = getProductosUseCase()
                _state.update { it.copy(productos = productos, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun updateName(name: String) {
        _state.update { it.copy(nameProducto = name) }
    }

    fun updatePrecio(precio: String) {
        _state.update { it.copy(precioProducto = precio) }
    }

    fun updateUrl(url: String) {
        _state.update { it.copy(urlProducto = url) }
    }

    fun updateCategoria(categoriaId: String) {
        _state.update { it.copy(codeCategoriaProducto = categoriaId) }
    }

    fun guardarProducto() {
        val current = _state.value
        if (current.nameProducto.isBlank() || current.precioProducto.isBlank()) {
            _state.update { it.copy(error = "Nombre y precio son obligatorios") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val nuevo = Producto(
                codeCategoriaProducto = current.codeCategoriaProducto,
                codeProducto = "",
                nameProducto = current.nameProducto,
                precioProducto = current.precioProducto,
                urlProducto = current.urlProducto,
                estadoProducto = "Activo"
            )
            val success = createProductoUseCase(nuevo)
            if (success) {
                _state.update { it.copy(isLoading = false, isSuccess = true, nameProducto = "", precioProducto = "", urlProducto = "") }
                cargarProductos()
            } else {
                _state.update { it.copy(isLoading = false, error = "Error al guardar el producto") }
            }
        }
    }

    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            val success = deleteProductoUseCase(producto.codeProducto)
            if (success) {
                cargarProductos()
            }
        }
    }

    fun actualizarProducto(
        producto: Producto,
        nuevoNombre: String,
        nuevoPrecio: String,
        nuevaUrl: String
    ) {
        if (nuevoNombre.isBlank() || nuevoPrecio.isBlank()) {
            _state.update { it.copy(error = "Nombre y precio son obligatorios") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val updated = producto.copy(
                nameProducto = nuevoNombre,
                precioProducto = nuevoPrecio,
                urlProducto = nuevaUrl
            )
            val success = updateProductoUseCase(updated)
            if (success) {
                _state.update { it.copy(isLoading = false, isSuccess = true) }
                cargarProductos()
            } else {
                _state.update { it.copy(isLoading = false, error = "Error al actualizar el producto") }
            }
        }
    }
}
