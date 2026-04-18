package com.andresdevs.restaurant.data.mapper

import com.andresdevs.restaurant.data.local.entity.ProductCacheEntity
import com.andresdevs.restaurant.domain.model.Producto

fun Producto.toCacheEntity(): ProductCacheEntity {
    return ProductCacheEntity(
        codeProducto = codeProducto,
        codeCategoriaProducto = codeCategoriaProducto,
        nameProducto = nameProducto,
        precioProducto = precioProducto,
        urlProducto = urlProducto,
        estadoProducto = estadoProducto
    )
}

fun ProductCacheEntity.toDomain(): Producto {
    return Producto(
        codeCategoriaProducto = codeCategoriaProducto,
        codeProducto = codeProducto,
        nameProducto = nameProducto,
        precioProducto = precioProducto,
        urlProducto = urlProducto,
        estadoProducto = estadoProducto
    )
}
