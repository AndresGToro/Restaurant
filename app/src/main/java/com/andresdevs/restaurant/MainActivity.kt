package com.andresdevs.restaurant

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.andresdevs.restaurant.ui.theme.RestaurantTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.andresdevs.restaurant.metodos.botonCRUD
import com.andresdevs.restaurant.metodos.cajaTextoFondo
import com.andresdevs.restaurant.metodos.contrasenaFondo
import com.andresdevs.restaurant.metodos.imagenLogoInicioSesion
import com.andresdevs.restaurant.metodos.tituloBlanco
import com.andresdevs.restaurant.navegacioninferior.PantallaPrincipal

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantTheme {
                // Pantalla con imagen de fondo transparente
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Imagen de fondo
                    Image(
                        painter = painterResource(id = R.drawable.fondo),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color.Black.copy(alpha = 0.1f)
                            ),
                        contentScale = ContentScale.Crop
                    )
                    // Contenido de la pantalla
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Contenido de tu pantalla aquí
                        imagenLogoInicioSesion()
                        tituloBlanco("Alitas BBQ")
                        cajaTextoFondo("Usuario")
                        contrasenaFondo("Contraseña")
                        botonCRUD("Iniciar Sesión") {
                            startActivity(Intent(this@MainActivity, PantallaPrincipal::class.java))
                        }
                        Spacer(modifier = Modifier.height(90.dp))
                    }
                }
            }
        }
    }
}