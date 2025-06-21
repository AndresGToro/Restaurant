package com.andresdevs.restaurant.data.firebase

import com.andresdevs.restaurant.data.model.UsuarioDto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume

class UsuarioFirebaseService {
    private val database = FirebaseDatabase.getInstance().getReference("Usuario")

    suspend fun getAllUsuarios(): List<UsuarioDto> = suspendCancellableCoroutine { cont ->
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lista = mutableListOf<UsuarioDto>()
                for (item in snapshot.children) {
                    val usuario = item.getValue(UsuarioDto::class.java)
                    usuario?.let { lista.add(it) }
                }
                cont.resume(lista)
            }

            override fun onCancelled(error: DatabaseError) {
                cont.resume(emptyList())
            }
        })
    }


    // suspend fun createUsuario(usuario: UsuarioDto): Boolean {
    //     return try {
    //         val newRef = database.push()
    //         val usuarioWithId = usuario.copy(codeUsuario = newRef.key ?: usuario.codeUsuario)
    //         newRef.setValue(usuarioWithId).await()
    //         true
    // }


    // suspend fun updateUsuario(usuario: UsuarioDto): Boolean {
    //     return try {
    //         database.child(usuario.codeUsuario).setValue(usuario).await()
    //     }catch (e: Exception){
    //         false
    //     }
    // }

    suspend fun deleteUsuario(usuarioId: String): Boolean {
        return try {
            database.child(usuarioId).removeValue().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
