package com.andresdevs.restaurant.domain.usecase.producto

import com.andresdevs.restaurant.domain.model.Producto
import com.andresdevs.restaurant.domain.repository.ProductoRepository

class GetProductoUseCase (
    private val repository: ProductoRepository
){
    suspend operator fun invoke(): List<Producto> {
        return repository.getProductos()
    }
}