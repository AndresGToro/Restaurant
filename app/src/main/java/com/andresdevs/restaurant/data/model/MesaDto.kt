package com.andresdevs.restaurant.data.model

data class MesaDto(
    val id: String = "",
    val numero: Int = 0,
    val capacidad: Int = 0,
    val estado: String = "DISPONIBLE"
)
