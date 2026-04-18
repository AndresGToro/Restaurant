package com.andresdevs.restaurant.domain.model

data class IvaConfig(
    val tasa: Double = 19.0, // Valor por defecto
    val moneda: String = "COP",
    val id: String = "default"
)
