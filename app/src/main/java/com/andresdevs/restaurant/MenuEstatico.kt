package com.andresdevs.restaurant

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.andresdevs.restaurant.metodos.imagenMenu
import com.andresdevs.restaurant.metodos.textoColorAlitas
import com.andresdevs.restaurant.metodos.tituloNegro
import com.andresdevs.restaurant.modulo.Categoria
import com.andresdevs.restaurant.modulo.IvaUpdate
import com.andresdevs.restaurant.modulo.Producto
import com.andresdevs.restaurant.modulo.Usuario

//=============================  MENU ESTATICO   =============================
@Composable
fun menuEstatico() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        tituloNegro("Menú principal")
        //FILA 1
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            //PRIMERO VISUALIZAR MENU
            Card(
                modifier = Modifier
                    .size(width = 160.dp, height = 160.dp)
                    .clickable {

                    },
                colors = CardDefaults.cardColors(Color(0xff8e2301))
            ) {
                contenedorCardVisualizarMenuTexto()
            }

            //SEGUNDO USER
            Card(
                modifier = Modifier
                    .size(width = 160.dp, height = 160.dp)
                    .clickable {
                        val intent = Intent(context, Usuario::class.java)
                        context.startActivity(intent)
                    },
                colors = CardDefaults.cardColors(Color(0xff8e2301))

            ) {
                contenedorCardUsuarioTexto()
            }
        }

        //FILA 2
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            //TERCER GESTIONAR CATEROGIA
            Card(
                modifier = Modifier
                    .size(width = 160.dp, height = 160.dp)
                    .clickable {
                        val intent = Intent(context, Categoria::class.java)
                        context.startActivity(intent)
                    },
                colors = CardDefaults.cardColors(Color(0xff8e2301))
            ) {
                contenedorCardGestionarCategoriaTexto()
            }

            //CUARTO GESTIONAR PRODUCTOS
            Card(
                modifier = Modifier
                    .size(width = 160.dp, height = 160.dp)
                    .clickable {
                        val intent = Intent(context, Producto::class.java)
                        context.startActivity(intent)
                    },
                colors = CardDefaults.cardColors(Color(0xff8e2301))
            ) {
                contenedorCardProductosTexto()
            }
        }

        //FILA 3
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            //QUINTO LISTA
            Card(
                modifier = Modifier
                    .size(width = 160.dp, height = 160.dp),
                colors = CardDefaults.cardColors(Color(0xff8e2301))
            ) {
                contenedorCardListaTexto()
            }

            //Sexto boton IVA
            Card(
                modifier = Modifier
                    .size(width = 160.dp, height = 160.dp)
                    .clickable {
                        val intent = Intent(context, IvaUpdate::class.java)
                        context.startActivity(intent)
                    },
                colors = CardDefaults.cardColors(Color(0xff8e2301))
            ) {
                contenedorCardIvaTexto()
            }
        }
    }
}

//================================================================
//imagenes y texto
@Composable
fun contenedorCardUsuarioTexto() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imagenMenu(R.drawable.user)
        textoColorAlitas("Usuario")

    }
}

@Composable
fun contenedorCardGestionarCategoriaTexto() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imagenMenu(R.drawable.products)
        textoColorAlitas("Gestionar categoría")
    }
}

@Composable
fun contenedorCardProductosTexto() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imagenMenu(R.drawable.wingscombo)
        textoColorAlitas("Gestionar productos")
    }
}

@Composable
fun contenedorCardVisualizarMenuTexto() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imagenMenu(R.drawable.menu)
        textoColorAlitas("Menú")
    }
}

@Composable
fun contenedorCardListaTexto() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imagenMenu(R.drawable.list)
        textoColorAlitas("Lista")
    }
}

@Composable
fun contenedorCardIvaTexto() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imagenMenu(R.drawable.interestrate)
        textoColorAlitas("Iva")
    }
}