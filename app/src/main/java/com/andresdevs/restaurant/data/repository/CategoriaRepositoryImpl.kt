package com.andresdevs.restaurant.data.repository

import com.andresdevs.restaurant.data.firebase.CategoriaFirebaseService
import com.andresdevs.restaurant.data.mapper.toDto
import com.andresdevs.restaurant.domain.model.Categoria
import com.andresdevs.restaurant.domain.repository.CategoriaRepository

class CategoriaRepositoryImpl(
    private val service: CategoriaFirebaseService
) : CategoriaRepository {

    override suspend fun getCategorias(): List<Categoria> {
        return service.getAllCategorias().map { it.toDomain() }
    }

    override suspend fun createCategoria(categoria: Categoria): Boolean {
        return service.createCategoria(categoria.toDto())
    }

    override suspend fun updateCategoria(categoria: Categoria): Boolean {
        return service.updateCategoria(categoria.toDto())
    }

    override suspend fun deleteCategoria(id: String): Boolean {
        return service.deleteCategoria(id)
    }
}