package com.andresdevs.restaurant.data.mapper

import com.andresdevs.restaurant.data.model.ProductoDto
import com.andresdevs.restaurant.domain.model.Producto

fun Producto.toDto(): ProductoDto {
    return ProductoDto(
        codeCategoriaProducto = codeCategoriaProducto,
        codeProducto = codeProducto,
        nameProducto = nameProducto,
        precioProducto = precioProducto,
        urlProducto = urlProducto,
        estadoProducto = estadoProducto,
    )
}

fun ProductoDto.toDomain(): Producto {
    return Producto(
        codeCategoriaProducto = codeCategoriaProducto,
        codeProducto = codeProducto,
        nameProducto = nameProducto,
        precioProducto = precioProducto,
        urlProducto = urlProducto,
        estadoProducto = estadoProducto,
    )
}