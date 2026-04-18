package com.andresdevs.restaurant.presentation.usuario

import com.andresdevs.restaurant.domain.model.Usuario

data class UsuarioState(
    val isLoading: Boolean = false,
    val usuarios: List<Usuario> = emptyList(),
    val error: String? = null,
    val isSuccess: Boolean = false,
    val cedula: String = "",
    val nombreCompleto: String = "",
    val usuario: String = "",
    val cargo: String = "",
    val celular: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val direccion: String = ""
)
