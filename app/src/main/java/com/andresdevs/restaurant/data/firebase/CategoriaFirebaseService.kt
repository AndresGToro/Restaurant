package com.andresdevs.restaurant.data.firebase

import com.andresdevs.restaurant.core.constants.FirebaseCollections
import com.andresdevs.restaurant.data.model.CategoriaDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoriaFirebaseService @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val collection = firestore.collection(FirebaseCollections.CATEGORIES)

    suspend fun getAllCategorias(): List<CategoriaDto> {
        val snapshot = collection.get().await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(CategoriaDto::class.java)?.copy(
                codeCategoria = doc.id
            )
        }
    }

    suspend fun createCategoria(categoria: CategoriaDto): Boolean {
        return try {
            val docId = if (categoria.codeCategoria.isBlank()) {
                collection.document().id
            } else {
                categoria.codeCategoria
            }
            collection.document(docId).set(categoria.copy(codeCategoria = docId)).await()
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun updateCategoria(categoria: CategoriaDto): Boolean {
        if (categoria.codeCategoria.isBlank()) return false
        return try {
            collection.document(categoria.codeCategoria).set(categoria).await()
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun deleteCategoria(categoriaId: String): Boolean {
        if (categoriaId.isBlank()) return false
        return try {
            collection.document(categoriaId).delete().await()
            true
        } catch (_: Exception) {
            false
        }
    }
}
