package com.andresdevs.restaurant.data.model

import com.andresdevs.restaurant.domain.model.Categoria

data class CategoriaDto(
    val codeCategoria: String = "",
    val name: String = "",
    val url: String = "",
    val estado: String = "",
) {
    fun toDomain(): Categoria {
        return Categoria(
            codeCategoria = codeCategoria,
            nombre = name,
            imagenUrl = url,
            estado = estado
        )
    }

    companion object {
        fun fromDomain(categoria: Categoria): CategoriaDto {
            return CategoriaDto(
                codeCategoria = categoria.codeCategoria,
                name = categoria.nombre,
                url = categoria.imagenUrl,
                estado = categoria.estado
            )
        }
    }
}
