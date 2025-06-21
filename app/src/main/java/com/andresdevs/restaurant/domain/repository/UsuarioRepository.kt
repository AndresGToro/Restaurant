package com.andresdevs.restaurant.domain.repository

import com.andresdevs.restaurant.domain.model.Usuario

interface UsuarioRepository {
    suspend fun getUsuarios(): List<Usuario>
    suspend fun createUsuario(usuario: Usuario): Boolean
    suspend fun updateUsuario(usuario: Usuario): Boolean
    suspend fun deleteUsuario(id: String): Boolean
}