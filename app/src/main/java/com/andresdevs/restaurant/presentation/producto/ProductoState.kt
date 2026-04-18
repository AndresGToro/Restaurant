package com.andresdevs.restaurant.presentation.producto

import com.andresdevs.restaurant.domain.model.Producto

data class ProductoState(
    val isLoading: Boolean = false,
    val productos: List<Producto> = emptyList(),
    val error: String? = null,
    
    // Campos para el formulario
    val nameProducto: String = "",
    val precioProducto: String = "",
    val urlProducto: String = "",
    val codeCategoriaProducto: String = "",
    val isSuccess: Boolean = false
)
