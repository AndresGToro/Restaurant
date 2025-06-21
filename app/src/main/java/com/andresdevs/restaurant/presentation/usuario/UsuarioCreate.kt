package com.andresdevs.restaurant.presentation.usuario

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
import com.andresdevs.restaurant.presentation.common.components.cajaNumerosEnteros
import com.andresdevs.restaurant.presentation.common.components.cajaTexto
import com.andresdevs.restaurant.presentation.common.components.contrasena
import com.andresdevs.restaurant.presentation.common.components.menuBox
import com.andresdevs.restaurant.presentation.common.components.tituloNegro
import com.andresdevs.restaurant.ui.theme.RestaurantTheme

class UsuarioCreate : ComponentActivity() {
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
                        tituloNegro("Usuario")
                        cajaNumerosEnteros("Cédula")
                        cajaTexto("Nombre completo")
                        cajaTexto("Nombre usuario")
                        menuBox()
                        cajaNumerosEnteros("Celular")
                        cajaTexto("Correo")
                        contrasena("Contraseña")
                        cajaTexto("Dirección")
                        botonCRUD("Crear") {
                            Toast.makeText(
                                this@UsuarioCreate,
                                "Usuario creado !!!",
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

