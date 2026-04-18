package com.andresdevs.restaurant.domain.model

enum class UserRole(val displayName: String) {
    ADMIN("Administrador"),
    MESERO("Mesero"),
    CAJERO("Cajero"),
    COCINA("Cocina"),
    CLIENTE("Cliente")
}
