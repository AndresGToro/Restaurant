package com.andresdevs.restaurant.presentation.categoria

import com.andresdevs.restaurant.domain.model.Categoria

sealed class CategoriaEvent {
    object LoadCategorias : CategoriaEvent()
    data class CrearCategoria(val categoria: Categoria) : CategoriaEvent()
    data class ActualizarCategoria(val categoria: Categoria) : CategoriaEvent()
    data class EliminarCategoria(val id: String) : CategoriaEvent()
}
