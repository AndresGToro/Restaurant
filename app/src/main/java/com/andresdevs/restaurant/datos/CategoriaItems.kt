package com.andresdevs.restaurant.datos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

data class CategoriaItems(
    val codeCategoria: String,
    val name: String,
    val url: String,
)

// Modificamos getCategoriaItems2 para que devuelva directamente la lista
suspend fun getCategoriaItems2(): List<CategoriaItems> {
    return suspendCoroutine { continuation ->
        val firebaseDatabase = Firebase.database
        val myRef = firebaseDatabase.getReference("Categoría")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<CategoriaItems>()
                for (childSnapshot in snapshot.children) {
                    val codeCategoria =
                        childSnapshot.child("codeCategoria").getValue(String::class.java)
                    val name = childSnapshot.child("name").getValue(String::class.java)
                    val url = childSnapshot.child("url").getValue(String::class.java)
                    if (codeCategoria != null && name != null && url != null) {
                        items.add(CategoriaItems(codeCategoria, name, url))
                    }
                }

                // Devolvemos la lista directamente
                continuation.resume(items)
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error
            }
        })
    }
}

@Composable
fun getCategoriaItems(): List<CategoriaItems> {
    var items by remember { mutableStateOf(emptyList<CategoriaItems>()) }

    LaunchedEffect(Unit) {
        // Llamamos a getCategoriaItems2 y obtenemos la lista directamente
        val itemList = getCategoriaItems2()
        // Actualizamos el estado con la lista obtenida
        items = itemList
    }

    return items
}

// Función para actualizar datos en Firebase Realtime Database
fun updateCategoriaItem(codeCategoria: String, updatedName: String, updatedUrl: String) {
    // Utiliza el path adecuado en tu base de datos
    val databaseReference = Firebase.database.getReference("Categoría/$codeCategoria")

    // Crea un objeto CategoriaItems con los nuevos valores
    val updatedItem = CategoriaItems(codeCategoria, updatedName, updatedUrl)

    // Actualiza los datos en Firebase
    databaseReference.setValue(updatedItem)
        .addOnSuccessListener {
            // Maneja el éxito, si es necesario
        }
        .addOnFailureListener {
            // Maneja el fallo, si es necesario
        }
}

fun deleteCategoriaItem(item: CategoriaItems) {
    val databaseReference = Firebase.database.getReference("Categoría")
    // Utiliza el método removeValue para eliminar el elemento de la base de datos
    databaseReference.child(item.codeCategoria).removeValue()
        .addOnSuccessListener {
            // Maneja el éxito, si es necesario
            // Por ejemplo, muestra un mensaje de éxito o realiza otras operaciones
        }
        .addOnFailureListener {
            // Maneja el fallo, si es necesario
            // Por ejemplo, muestra un mensaje de error o realiza otras operaciones
        }
}
