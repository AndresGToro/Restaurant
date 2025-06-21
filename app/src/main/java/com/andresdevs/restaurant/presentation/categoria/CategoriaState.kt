package com.andresdevs.restaurant.presentation.categoria

import com.andresdevs.restaurant.domain.model.Categoria

data class CategoriaState(
    val isLoading: Boolean = false,
    val categorias: List<Categoria> = emptyList(),
    val nombre: String = "",
    val url: String = "",
    val error: String? = null
)
