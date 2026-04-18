package com.andresdevs.restaurant.data.firebase

import com.andresdevs.restaurant.core.constants.FirebaseCollections
import com.andresdevs.restaurant.data.model.ProductoDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductoFirebaseService @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val collection = firestore.collection(FirebaseCollections.PRODUCTS)

    suspend fun getAllProductos(): List<ProductoDto> {
        val snapshot = collection.get().await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(ProductoDto::class.java)?.copy(
                codeProducto = doc.id
            )
        }
    }

    suspend fun createProducto(producto: ProductoDto): Boolean {
        return try {
            val docId = if (producto.codeProducto.isBlank()) {
                collection.document().id
            } else {
                producto.codeProducto
            }
            collection.document(docId).set(producto.copy(codeProducto = docId)).await()
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun updateProducto(producto: ProductoDto): Boolean {
        if (producto.codeProducto.isBlank()) return false
        return try {
            collection.document(producto.codeProducto).set(producto).await()
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun deleteProducto(productoId: String): Boolean {
        if (productoId.isBlank()) return false
        return try {
            collection.document(productoId).delete().await()
            true
        } catch (_: Exception) {
            false
        }
    }
}
