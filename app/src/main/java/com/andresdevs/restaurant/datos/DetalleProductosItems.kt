package com.andresdevs.restaurant.datos


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

data class DetalleProductosItems(
    val codeProducto: String,
    val nameProducto: String,
    val precioProducto: String,
    val cantidadProducto: String,
    val subtotalProducto: String,
    val urlProducto: String,
)
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun detalleProductosItemList(itemList: List<DetalleProductosItems>) {
    val deletedItem = remember { mutableStateListOf<DetalleProductosItems>() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp), // Ajusta la altura según sea necesario
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(
                items = itemList,
                itemContent = { _, item ->
                    AnimatedVisibility(
                        visible = !deletedItem.contains(item),
                        enter = expandHorizontally(),
                        exit = shrinkHorizontally(animationSpec = tween(durationMillis = 1000))
                    ) {
                        Card(
                            modifier =
                            Modifier
                                .width(200.dp) // Ancho de cada elemento, puedes ajustarlo según tus necesidades
                                .height(200.dp)
                                .padding(10.dp, 5.dp, 10.dp, 5.dp)
                                .background(Color.Transparent),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                GlideImage(
                                    model = item.urlProducto,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp) // Altura de la imagen, puedes ajustarlo según tus necesidades
                                        .clip(RoundedCornerShape(5.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre la imagen y el texto
                                Text(
                                    text = item.nameProducto,
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight() // Permitir que el texto tenga su propio tamaño de altura
                                        .padding(8.dp)
                                )
                                println("probando ${item.nameProducto}")
                            }
                        }
                    }
                }
            )
        }
    }
}