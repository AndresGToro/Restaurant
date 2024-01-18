package com.andresdevs.restaurant

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.andresdevs.restaurant.datos.CategoriaItems
import com.andresdevs.restaurant.metodos.botonCRUD
import com.andresdevs.restaurant.metodos.cajaTexto
import com.andresdevs.restaurant.metodos.tituloNegro
import com.andresdevs.restaurant.metodos.urlImagen
import com.andresdevs.restaurant.ui.theme.RestaurantTheme
import com.google.firebase.Firebase
import com.google.firebase.database.database

class CategoriaCreate : ComponentActivity() {

    val firebaseDatabase = Firebase.database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    color = MaterialTheme.colorScheme.background,
                    contentColor = Color.Black
                ) {
                    Column {
                        //
                        val titulo = tituloNegro("Categoría")
                        val nombreCategoria = cajaTexto("Nombre categoría")
                        val url = urlImagen()

                        botonCRUD("Crear") {
                            val contactsRef = firebaseDatabase.reference.child(titulo)
                            val newDataKey = contactsRef.push().key
                            val contactRef = contactsRef.child(newDataKey!!)
                            val contact = CategoriaItems(newDataKey, nombreCategoria, url)
                            contactRef.setValue(contact)

                            Toast.makeText(
                                this@CategoriaCreate, "Categoría creada !!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }
                }
            }
        }
    }
}
