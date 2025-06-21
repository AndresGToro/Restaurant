package com.andresdevs.restaurant.presentation.navigation.bottomnav

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.andresdevs.restaurant.data.model.detalleProductosItemList
import com.andresdevs.restaurant.data.model.listaDetallesProductos

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
