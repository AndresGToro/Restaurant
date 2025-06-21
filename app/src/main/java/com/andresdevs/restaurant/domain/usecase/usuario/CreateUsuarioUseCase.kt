package com.andresdevs.restaurant.domain.usecase.usuario

import com.andresdevs.restaurant.domain.model.Usuario
import com.andresdevs.restaurant.domain.repository.UsuarioRepository

class CreateUsuarioUseCase (
    private val repository: UsuarioRepository
){
    suspend operator fun invoke(usuario: Usuario): Boolean {
        return repository.createUsuario(usuario)

    }
}