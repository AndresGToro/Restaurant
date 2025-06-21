package com.andresdevs.restaurant.domain.repository

import com.andresdevs.restaurant.domain.model.Categoria

interface CategoriaRepository {
    suspend fun getCategorias(): List<Categoria>
    suspend fun createCategoria(categoria: Categoria): Boolean
    suspend fun updateCategoria(categoria: Categoria): Boolean
    suspend fun deleteCategoria(id: String): Boolean
}