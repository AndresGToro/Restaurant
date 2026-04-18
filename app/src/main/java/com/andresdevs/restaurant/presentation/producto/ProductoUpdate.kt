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
import com.andresdevs.restaurant.presentation.common.components.botonCRUD
import com.andresdevs.restaurant.presentation.common.components.cajaNumerosDecimales
import com.andresdevs.restaurant.presentation.common.components.cajaTexto2
import com.andresdevs.restaurant.presentation.common.components.estado
import com.andresdevs.restaurant.presentation.common.components.tituloNegro
import com.andresdevs.restaurant.presentation.common.components.urlImagen
import com.andresdevs.restaurant.ui.theme.RestaurantTheme
import com.google.firebase.Firebase
import com.google.firebase.database.database

class ProductoUpdate: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Cargar datos
        val codeCategoriaProducto = intent.getStringExtra("codeCategoriaProducto") ?: ""
        val codeProducto = intent.getStringExtra("codeProducto") ?: ""
        val nameProducto = intent.getStringExtra("nameProducto") ?: ""
        val precioProducto = intent.getStringExtra("precioProducto") ?: ""
        val urlProducto = intent.getStringExtra("urlProducto") ?: ""

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
                        tituloNegro("Producto")
                        var newNombreProducto = cajaTexto2("Nombre producto",nameProducto)
                        var newPrecioProducto = cajaNumerosDecimales("Precio", precioProducto)
                        var urlNew = urlImagen("Url imagen", urlProducto)
                        var estadoNew = estado()
                        if(estadoNew!="Activo"&&estadoNew!="Inactivo"){
                            estadoNew = "Activo"
                        }
                        botonCRUD("Actualizar") {
                            val productoMap = mapOf(
                                "codeCategoriaProducto" to codeCategoriaProducto,
                                "codeProducto" to codeProducto,
                                "nameProducto" to newNombreProducto,
                                "precioProducto" to newPrecioProducto,
                                "urlProducto" to urlNew,
                                "estadoProducto" to estadoNew
                            )
                            Firebase.database.getReference("Producto")
                                .child(codeProducto)
                                .setValue(productoMap)
                            Toast.makeText(
                                this@ProductoUpdate,
                                "Producto actualizado !!!",
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

