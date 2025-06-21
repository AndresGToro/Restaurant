package com.andresdevs.restaurant.data.firebase

import com.andresdevs.restaurant.data.model.ProductoDto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume

class ProductoFirebaseService {
    private val database = FirebaseDatabase.getInstance().getReference("Producto")

    suspend fun getAllProductos(): List<ProductoDto> = suspendCancellableCoroutine{ cont ->
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lista = mutableListOf<ProductoDto>()
                for (item in snapshot.children) {
                    val producto = item.getValue(ProductoDto::class.java)
                    producto?.let { lista.add(it) }
                }
                cont.resume(lista)
            }

            override fun onCancelled(error: DatabaseError) {
                cont.resume(emptyList())
            }
        })
    }

    suspend fun createProducto(producto: ProductoDto): Boolean {
        return try {
            val newRef = database.push()
            val productoWithId = producto.copy(codeProducto = newRef.key ?: producto.codeProducto)
            newRef.setValue(productoWithId).await()
            true
            } catch (e: Exception) {
            false
        }
    }

    suspend fun updateProducto(producto: ProductoDto): Boolean {
        return try {
            database.child(producto.codeProducto).setValue(producto).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteProducto(productoId: String): Boolean {
        return try {
            database.child(productoId).removeValue().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
