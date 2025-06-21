package com.andresdevs.restaurant.data.mapper

import com.andresdevs.restaurant.data.model.CategoriaDto
import com.andresdevs.restaurant.domain.model.Categoria

fun Categoria.toDto(): CategoriaDto {
    return CategoriaDto(
        codeCategoria = codeCategoria,
        name = nombre,
        url = imagenUrl,
        estado = estado
    )
}

fun CategoriaDto.toDomain(): Categoria {
    return Categoria(
        codeCategoria = codeCategoria,
        nombre = name,
        imagenUrl = url,
        estado = estado
    )
}
