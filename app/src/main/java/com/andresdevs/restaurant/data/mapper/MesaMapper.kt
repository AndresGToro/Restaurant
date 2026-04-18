package com.andresdevs.restaurant.data.mapper

import com.andresdevs.restaurant.data.model.MesaDto
import com.andresdevs.restaurant.domain.model.EstadoMesa
import com.andresdevs.restaurant.domain.model.Mesa

fun Mesa.toDto(): MesaDto {
    return MesaDto(
        id = id,
        numero = numero,
        capacidad = capacidad,
        estado = estado.name
    )
}

fun MesaDto.toDomain(): Mesa {
    return Mesa(
        id = id,
        numero = numero,
        capacidad = capacidad,
        estado = try {
            EstadoMesa.valueOf(estado)
        } catch (e: Exception) {
            EstadoMesa.DISPONIBLE
        }
    )
}
