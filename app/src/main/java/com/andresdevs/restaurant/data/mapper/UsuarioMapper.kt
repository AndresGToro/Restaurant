package com.andresdevs.restaurant.data.mapper

import com.andresdevs.restaurant.data.model.UsuarioDto
import com.andresdevs.restaurant.domain.model.Usuario

fun Usuario.toDto(): UsuarioDto {
    return UsuarioDto(
         cedula=cedula,
         nombreCompleto= nombreCompleto,
         usuario= usuario,
         cargo = cargo,
         celular = celular,
         correo = correo,
         contrasena = contrasena,
         direccion = direccion,
    )
}

fun UsuarioDto.toDomain(): Usuario {
    return Usuario(
        cedula=cedula,
        nombreCompleto= nombreCompleto,
        usuario= usuario,
        cargo = cargo,
        celular = celular,
        correo = correo,
        contrasena = contrasena,
        direccion = direccion
    )
}