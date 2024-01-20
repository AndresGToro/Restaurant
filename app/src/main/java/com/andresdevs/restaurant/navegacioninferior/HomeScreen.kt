package com.andresdevs.restaurant.navegacioninferior

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.andresdevs.restaurant.datos.categoriaItemList
import com.andresdevs.restaurant.datos.categoriaItemListHorizontal
import com.andresdevs.restaurant.datos.getCategoriaItems
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
        categoriaItemListHorizontal(itemList = getCategoriaItems())
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun cargar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f/9f) // Puedes ajustar el aspect ratio según tus necesidades
    ) {
        GlideImage(
            model = "https://www.unileverfoodsolutions.com.co/dam/global-ufs/mcos/NOLA/calcmenu/recipes/col-recipies/recetas-fruco-bbq/Alitas-BBQ-1200x709.jpg",
            contentDescription = "ImageItem2",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

    }
}