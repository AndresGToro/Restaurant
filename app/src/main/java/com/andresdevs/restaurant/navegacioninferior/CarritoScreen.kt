package com.andresdevs.restaurant.navegacioninferior

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.andresdevs.restaurant.datos.detalleProductosItemList
import com.andresdevs.restaurant.datos.listaDetallesProductos
import com.andresdevs.restaurant.datos.obtenerListaProductos

@Composable
fun CarritoScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Carrito de compras")
       // detalleProductosItemList(itemList= obtenerListaProductos())
        detalleProductosItemList(itemList = listaDetallesProductos)

    }
}
