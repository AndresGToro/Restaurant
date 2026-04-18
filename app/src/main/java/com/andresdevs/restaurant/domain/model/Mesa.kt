package com.andresdevs.restaurant.domain.model

enum class EstadoMesa(val displayName: String) {
    DISPONIBLE("Disponible"),
    OCUPADA("Ocupada"),
    RESERVADA("Reservada"),
    LIMPIEZA("En Limpieza")
}

data class Mesa(
    val id: String = "",
    val numero: Int = 0,
    val capacidad: Int = 0,
    val estado: EstadoMesa = EstadoMesa.DISPONIBLE
)
