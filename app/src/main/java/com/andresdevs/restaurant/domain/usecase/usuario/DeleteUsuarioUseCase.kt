package com.andresdevs.restaurant.domain.usecase.usuario

import com.andresdevs.restaurant.domain.repository.UsuarioRepository

class DeleteUsuarioUseCase (
    private val repository: UsuarioRepository
){
    suspend operator fun invoke(id: String): Boolean {
        return repository.deleteUsuario(id)
    }
}