package com.andresdevs.restaurant.data.repository

import com.andresdevs.restaurant.data.firebase.ProductoFirebaseService
import com.andresdevs.restaurant.data.mapper.toDto
import com.andresdevs.restaurant.domain.model.Producto
import com.andresdevs.restaurant.domain.repository.ProductoRepository

class ProductoRepositoryImpl (
    private val service: ProductoFirebaseService
): ProductoRepository {
    override suspend fun getProductos(): List<Producto> {
        return service.getAllProductos().map { it.toDomain() }
    }

    override suspend fun createProducto(Producto: Producto): Boolean {
        return service.createProducto(Producto.toDto())
    }

    override suspend fun updateProducto(Producto: Producto): Boolean {
        return service.updateProducto(Producto.toDto())
    }

    override suspend fun deleteProducto(id: String): Boolean {
        return service.deleteProducto(id)
    }

}