package com.andresdevs.restaurant.data.repository

import com.andresdevs.restaurant.data.firebase.UsuarioFirebaseService
import com.andresdevs.restaurant.data.mapper.toDomain
import com.andresdevs.restaurant.data.mapper.toDto
import com.andresdevs.restaurant.domain.model.Usuario
import com.andresdevs.restaurant.domain.repository.UsuarioRepository

class UsuarioRepositoryImpl(
    private val service: UsuarioFirebaseService
) : UsuarioRepository {
    override suspend fun getUsuarios(): List<Usuario> {
        return service.getAllUsuarios().map { it.toDomain() }
    }

    override suspend fun createUsuario(usuario: Usuario): Boolean {
        return service.createUsuario(usuario.toDto())
    }

    override suspend fun updateUsuario(usuario: Usuario): Boolean {
        return service.updateUsuario(usuario.toDto())
    }

    override suspend fun deleteUsuario(id: String): Boolean {
        return service.deleteUsuario(id)
    }
}
