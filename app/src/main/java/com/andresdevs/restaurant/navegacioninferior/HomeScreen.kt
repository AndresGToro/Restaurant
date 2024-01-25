package com.andresdevs.restaurant.navegacioninferior

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.andresdevs.restaurant.datos.categoriaItemListHorizontal
import com.andresdevs.restaurant.datos.getCategoriaItems
import com.andresdevs.restaurant.datos.getProductoItems
import com.andresdevs.restaurant.datos.productoPromoItemList
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        cargar()
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                categoriaItemListHorizontal(itemList = getCategoriaItems())
            }
        }
        // codigo categoria
        productoPromoItemList(itemList = getProductoItems(), "-Nor6DISCM91G6Kg7yTf")
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun cargar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f) // Puedes ajustar el aspect ratio según tus necesidades
    ) {
        GlideImage(
            model = "https://www.unileverfoodsolutions.com.co/dam/global-ufs/mcos/NOLA/calcmenu/recipes/col-recipies/recetas-fruco-bbq/Alitas-BBQ-1200x709.jpg",
            contentDescription = "ImageItem2",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

    }
}