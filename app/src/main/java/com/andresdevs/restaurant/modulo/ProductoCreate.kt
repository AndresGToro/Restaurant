package com.andresdevs.restaurant.modulo

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
import com.andresdevs.restaurant.metodos.botonCRUD
import com.andresdevs.restaurant.metodos.cajaNumerosDecimales
import com.andresdevs.restaurant.metodos.cajaTexto
import com.andresdevs.restaurant.metodos.menuBox2
import com.andresdevs.restaurant.metodos.tituloNegro
import com.andresdevs.restaurant.metodos.urlImagen
import com.andresdevs.restaurant.ui.theme.RestaurantTheme

class ProductoCreate: ComponentActivity() {
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
                        tituloNegro("Producto")
                        menuBox2()
                        cajaTexto("Nombre producto")
                        cajaNumerosDecimales("Precio")
                        urlImagen("Url producto", "")
                        botonCRUD("Crear") {
                            Toast.makeText(
                                this@ProductoCreate,
                                "Producto creado !!!",
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

