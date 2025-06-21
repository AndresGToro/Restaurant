package com.andresdevs.restaurant.domain.usecase.categoria

import com.andresdevs.restaurant.domain.model.Categoria
import com.andresdevs.restaurant.domain.repository.CategoriaRepository

class GetCategoriaUseCase (
    private val repository: CategoriaRepository
) {
    suspend operator fun invoke(): List<Categoria> {
        return repository.getCategorias()
    }
}