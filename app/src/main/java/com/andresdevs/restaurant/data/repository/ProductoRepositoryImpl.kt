package com.andresdevs.restaurant.data.repository

import com.andresdevs.restaurant.data.firebase.ProductoFirebaseService
import com.andresdevs.restaurant.data.local.dao.ProductCacheDao
import com.andresdevs.restaurant.data.mapper.toCacheEntity
import com.andresdevs.restaurant.data.mapper.toDomain
import com.andresdevs.restaurant.data.mapper.toDto
import com.andresdevs.restaurant.domain.model.Producto
import com.andresdevs.restaurant.domain.repository.ProductoRepository

class ProductoRepositoryImpl (
    private val service: ProductoFirebaseService,
    private val cacheDao: ProductCacheDao
): ProductoRepository {
    override suspend fun getProductos(): List<Producto> {
        return try {
            val remote = service.getAllProductos().map { it.toDomain() }
            cacheDao.upsertAll(remote.map { it.toCacheEntity() })
            remote
        } catch (_: Exception) {
            cacheDao.getAll().map { it.toDomain() }
        }
    }

    override suspend fun createProducto(producto: Producto): Boolean {
        val success = service.createProducto(producto.toDto())
        if (success) {
            getProductos()
        }
        return success
    }

    override suspend fun updateProducto(producto: Producto): Boolean {
        val success = service.updateProducto(producto.toDto())
        if (success) {
            getProductos()
        }
        return success
    }

    override suspend fun deleteProducto(id: String): Boolean {
        val success = service.deleteProducto(id)
        if (success) {
            cacheDao.deleteById(id)
        }
        return success
    }

}
