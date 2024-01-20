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
import com.andresdevs.restaurant.datos.updateCategoriaItem
import com.andresdevs.restaurant.metodos.botonCRUD
import com.andresdevs.restaurant.metodos.cajaTexto2
import com.andresdevs.restaurant.metodos.estado
import com.andresdevs.restaurant.metodos.tituloNegro
import com.andresdevs.restaurant.metodos.urlImagen
import com.andresdevs.restaurant.ui.theme.RestaurantTheme

class CategoriaUpdate : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Cargar datos
        val codigoUnico = intent.getStringExtra("codigoUnicoFilaCategoria") ?: ""
        val nombreCategoria = intent.getStringExtra("nombreCategoria") ?: ""
        val urlImagen = intent.getStringExtra("urlImagen") ?: ""

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
                        tituloNegro("Categoría")
                        var newNombreCategoria = cajaTexto2("Nombre categoría", nombreCategoria)
                        var newUrlImgCategoria = urlImagen("Url imagen", urlImagen)
                        var estadoNew = estado()
                        if(estadoNew!="Activo"||estadoNew!="Inactivo"){
                            estadoNew = "Activo"
                        }
                        botonCRUD("Actualizar") {
                            updateCategoriaItem(codigoUnico,newNombreCategoria, newUrlImgCategoria, estadoNew)
                            Toast.makeText(
                                this@CategoriaUpdate,
                                "Categoría actualizada !!!",
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