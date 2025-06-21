package com.andresdevs.restaurant.presentation.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.andresdevs.restaurant.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

//=============================    IMAGENES    =============================
@Composable
fun imagenLogoInicioSesion() {
    Image(
        painter = painterResource(id = R.drawable.logosegundosinfondo),
        contentDescription = null,
        modifier = Modifier
            .size(250.dp)  // Ajusta el tamaño según tus necesidades
            .clip(CircleShape)  // Otra modificación que puedes aplicar según tus necesidades
    )
}


@Composable
fun imagenMenu(imagen: Int) {
    Box() {
        Image(
            painter = painterResource(id = imagen),
            contentDescription = null,
            modifier = Modifier
                .size(68.dp)
                .clip(CircleShape)// Ajusta el tamaño según tus necesidades
                .padding(bottom = 8.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun urlImagen(titulo: String, info: String): String {
    var urlValue by remember { mutableStateOf(info) }

    LaunchedEffect(info) { urlValue = info }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(value = urlValue,
            onValueChange = { newText ->
                urlValue = newText
            },
            label = { Text(titulo) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            trailingIcon = {
                if (urlValue.isNotEmpty()) {
                    Icon(imageVector = Icons.Filled.Clear,
                        contentDescription = "Limpiar",
                        modifier = Modifier
                            .clickable { urlValue = "" })
                }
            })

        GlideImage(
            model = urlValue,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .border(1.dp, color = Black),
            contentScale = ContentScale.Crop
        )
    }
    return urlValue
}