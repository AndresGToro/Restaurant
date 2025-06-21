package com.andresdevs.restaurant.data.mapper

import com.andresdevs.restaurant.data.model.DetalleProductoDto
import com.andresdevs.restaurant.domain.model.DetalleProducto

fun DetalleProducto.toDto(): DetalleProductoDto {
    return DetalleProductoDto(
   codeProducto = codeProducto,
   nameProducto = nameProducto,
   precioProducto = precioProducto,
   cantidadProducto = cantidadProducto,
   subtotalProducto = subtotalProducto,
   urlProducto = urlProducto,
    )
}

fun DetalleProductoDto.toDomain(): DetalleProducto {
    return DetalleProducto(
        codeProducto = codeProducto,
        nameProducto = nameProducto,
        precioProducto = precioProducto,
        cantidadProducto = cantidadProducto,
        subtotalProducto = subtotalProducto,
        urlProducto = urlProducto,
    )
}