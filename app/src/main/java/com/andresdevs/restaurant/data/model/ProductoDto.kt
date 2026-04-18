package com.andresdevs.restaurant.data.model

import com.andresdevs.restaurant.domain.model.Producto

data class ProductoDto(
    val codeCategoriaProducto: String = "",
    val codeProducto: String = "",
    val nameProducto: String = "",
    val precioProducto: String = "",
    val urlProducto: String = "",
    val estadoProducto: String = "",
) {
    fun toDomain(): Producto {
        return Producto(
            codeCategoriaProducto = codeCategoriaProducto,
            codeProducto = codeProducto,
            nameProducto = nameProducto,
            precioProducto = precioProducto,
            urlProducto = urlProducto,
            estadoProducto = estadoProducto
        )
    }

    companion object {
        fun fromDomain(producto: Producto): ProductoDto {
            return ProductoDto(
                codeCategoriaProducto = producto.codeCategoriaProducto,
                codeProducto = producto.codeProducto,
                nameProducto = producto.nameProducto,
                precioProducto = producto.precioProducto,
                urlProducto = producto.urlProducto,
                estadoProducto = producto.estadoProducto
            )
        }
    }
}
