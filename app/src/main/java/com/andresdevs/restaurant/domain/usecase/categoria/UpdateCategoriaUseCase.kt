package com.andresdevs.restaurant.domain.usecase.categoria

import com.andresdevs.restaurant.domain.model.Categoria
import com.andresdevs.restaurant.domain.repository.CategoriaRepository

class UpdateCategoriaUseCase (
    private val repository: CategoriaRepository
) {
    suspend operator fun invoke(categoria: Categoria): Boolean {
        return repository.updateCategoria(categoria)
    }
}