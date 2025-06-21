package com.andresdevs.restaurant.domain.repository

import com.andresdevs.restaurant.domain.model.Producto

interface ProductoRepository {
    suspend fun getProductos(): List<Producto>
    suspend fun createProducto(producto: Producto): Boolean
    suspend fun updateProducto(producto: Producto): Boolean
    suspend fun deleteProducto(id: String): Boolean
}