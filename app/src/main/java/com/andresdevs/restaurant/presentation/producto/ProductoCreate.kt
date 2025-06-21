package com.andresdevs.restaurant.presentation.producto

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
import com.andresdevs.restaurant.core.utils.removeAccents
import com.andresdevs.restaurant.data.model.ProductoDto
import com.andresdevs.restaurant.presentation.common.components.botonCRUD
import com.andresdevs.restaurant.presentation.common.components.cajaNumerosDecimales
import com.andresdevs.restaurant.presentation.common.components.cajaTexto
import com.andresdevs.restaurant.presentation.common.components.tituloNegro
import com.andresdevs.restaurant.presentation.common.components.urlImagen
import com.andresdevs.restaurant.ui.theme.RestaurantTheme
import com.google.firebase.Firebase
import com.google.firebase.database.database

class ProductoCreate: ComponentActivity() {

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
                        var titulo = tituloNegro("Producto")
                        titulo = titulo.removeAccents()
                        val nombreProducto = cajaTexto("Nombre producto")
                        val precio = cajaNumerosDecimales("Precio", "")
                        val url = urlImagen("Url imagen", "")
                        botonCRUD("Crear") {
                            val contactsRef = firebaseDatabase.reference.child(titulo)
                            val newDataKey = contactsRef.push().key
                            val contactRef = contactsRef.child(newDataKey!!)
                            //
                            val codigoUnico = intent.getStringExtra("codigoUnicoFilaCategoria") ?: ""

                            val contact = ProductoDto(codigoUnico,newDataKey, nombreProducto, precio, url, "Activo")
                            contactRef.setValue(contact)

                            Toast.makeText(
                                this@ProductoCreate,
                                "Producto creado !!!",
                                Toast.LENGTH_SHORT
                            ).show()
                           // finish()
                        }
                    }
                }
            }
        }
    }
}

