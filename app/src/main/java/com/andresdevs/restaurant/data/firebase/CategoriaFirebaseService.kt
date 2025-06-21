package com.andresdevs.restaurant.data.firebase

import com.andresdevs.restaurant.data.model.CategoriaDto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class CategoriaFirebaseService {

    private val database = FirebaseDatabase.getInstance().getReference("Categoria")

    suspend fun getAllCategorias(): List<CategoriaDto> = suspendCancellableCoroutine { cont ->
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lista = mutableListOf<CategoriaDto>()
                for (item in snapshot.children) {
                    val categoria = item.getValue(CategoriaDto::class.java)
                    categoria?.let { lista.add(it) }
                }
                cont.resume(lista)
            }

            override fun onCancelled(error: DatabaseError) {
                cont.resume(emptyList())
            }
        })
    }

    suspend fun createCategoria(categoria: CategoriaDto): Boolean {
        return try {
            val newRef = database.push()
            val categoriaWithId = categoria.copy(codeCategoria = newRef.key ?: categoria.codeCategoria)
            newRef.setValue(categoriaWithId).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateCategoria(categoria: CategoriaDto): Boolean {
        return try {
            database.child(categoria.codeCategoria).setValue(categoria).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteCategoria(categoriaId: String): Boolean {
        return try {
            database.child(categoriaId).removeValue().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
