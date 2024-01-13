package com.andresdevs.restaurant

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andresdevs.restaurant.ui.theme.RestaurantTheme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextField
import androidx.compose.ui.layout.ContentScale
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RestaurantTheme {
                // Pantalla con imagen de fondo transparente
                Box(
                    modifier = Modifier
                        .fillMaxSize()
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
                        imagen()
                        titulo()
                        cajaTexto()
                        contrasena()
                        botonIniciarSesion()
                        Spacer(modifier = Modifier.height(90.dp))
                    }


                }
            }
        }
    }

    @Composable
    fun botonIniciarSesion() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Button(
                onClick = {
                    val navegation = Intent(this@MainActivity, MenuPrincipal::class.java)
                    startActivity(navegation)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Iniciar Sesión")
            }
        }
    }
}

@Composable
fun imagen() {
    Image(
        painter = painterResource(id = R.drawable.logosegundosinfondo),
        contentDescription = null,
        modifier = Modifier
            .size(250.dp)  // Ajusta el tamaño según tus necesidades
            .clip(CircleShape)  // Otra modificación que puedes aplicar según tus necesidades
    )
}

@Composable
fun titulo() {
    Text(
        text = "Alitas BBQ",
        color = Color.White,
        fontSize = 30.sp,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // Puedes ajustar el valor según tus necesidades
            .padding(vertical = 16.dp)
    )
}

@Composable
private fun cajaTexto() {
    var text by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Usuario") },
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()

        )
    }
}


@Composable
private fun contrasena() {
    var text by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
    }
}

//Permite visaulizar IU COmputador
@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    RestaurantTheme {
        // Pantalla con imagen de fondo transparente
        Box(
            modifier = Modifier
                .fillMaxSize()
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
                imagen()
                titulo()
                cajaTexto()
                contrasena()
                //botonIniciarSesion()
                Spacer(modifier = Modifier.height(90.dp))
            }


        }
    }
}