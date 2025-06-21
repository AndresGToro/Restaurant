package com.andresdevs.restaurant.domain.usecase.categoria


import com.andresdevs.restaurant.domain.repository.CategoriaRepository

class DeleteCategoriaUseCase (
    private val repository: CategoriaRepository
) {
    suspend operator fun invoke(id: String): Boolean {
        return repository.deleteCategoria(id)
    }
}
