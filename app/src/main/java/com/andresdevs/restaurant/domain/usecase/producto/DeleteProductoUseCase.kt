package com.andresdevs.restaurant.domain.usecase.producto

import com.andresdevs.restaurant.domain.repository.ProductoRepository

class DeleteProductoUseCase (
    private val repository: ProductoRepository
){
    suspend operator fun invoke(id: String): Boolean {
        return repository.deleteProducto(id)
    }
}