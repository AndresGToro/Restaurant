package com.andresdevs.restaurant.domain.usecase.producto

import com.andresdevs.restaurant.domain.model.Producto
import com.andresdevs.restaurant.domain.repository.ProductoRepository

class UpdateProductoUseCase(
    private val repository: ProductoRepository
){
    suspend operator fun invoke(producto: Producto): Boolean {
        return repository.updateProducto(producto)

    }
}