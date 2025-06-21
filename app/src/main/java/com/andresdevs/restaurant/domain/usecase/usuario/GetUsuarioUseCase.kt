package com.andresdevs.restaurant.domain.usecase.usuario

import com.andresdevs.restaurant.domain.model.Usuario
import com.andresdevs.restaurant.domain.repository.UsuarioRepository

class GetUsuarioUseCase (
    private val repository: UsuarioRepository
){
    suspend operator fun invoke(): List<Usuario> {
        return repository.getUsuarios()
    }
}