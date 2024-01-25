package com.andresdevs.restaurant.modulo

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andresdevs.restaurant.datos.getProductoItems
import com.andresdevs.restaurant.datos.productoItemListMesero
import com.andresdevs.restaurant.ui.theme.RestaurantTheme

class ProductosVisualizar : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    contentColor = Color.Black
                ) {
                    val codigoUnico = intent.getStringExtra("codigoUnicoFilaCategoria") ?: ""
                    Log.e(TAG, "Codigo unico: $codigoUnico")
                    //carga lista
                    productoItemListMesero(itemList = getProductoItems(), codigoUnico)
                }
            }
        }
    }
}
