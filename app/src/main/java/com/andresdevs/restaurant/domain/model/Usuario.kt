package com.andresdevs.restaurant.domain.model

data class Usuario (
    val cedula: String,
    val nombreCompleto: String,
    val usuario: String,
    val cargo: String,
    val celular: String,
    val correo: String,
    val contrasena: String,
    val direccion: String
)