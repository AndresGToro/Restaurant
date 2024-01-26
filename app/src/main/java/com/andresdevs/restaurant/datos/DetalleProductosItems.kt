package com.andresdevs.restaurant.datos


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

//FUnciona inico
@Composable
fun obtenerListaProductos(): List<DetalleProductosItems> {
    // Aquí obtén tu lista de productos desde donde sea necesario
    return listOf(
        DetalleProductosItems(
            "1",
            "Producto 1",
            "$10.99",
            "2",
            "$21.98",
            "https://ejemplo.com/imagen1.jpg"
        )
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun detalleProductosItemList(itemList: List<DetalleProductosItems>) {
    val deletedItem = remember { mutableStateListOf<DetalleProductosItems>() }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(
                    items = itemList,
                    itemContent = { _, item ->
                        AnimatedVisibility(
                            visible = !deletedItem.contains(item),
                            enter = expandVertically(),
                            exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(10.dp, 5.dp, 10.dp, 5.dp)
                                    .background(Color.White),
                                shape = RoundedCornerShape(5.dp)
                            ) {
                                // Contenido de la Card
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        GlideImage(
                                            model = item.urlProducto,
                                            contentDescription = "ImageItem2",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(60.dp)
                                                .clip(CircleShape)
                                        )

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = item.nameProducto,
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontSize = 20.sp,
                                                    textAlign = TextAlign.Center
                                                ),
                                                modifier = Modifier
                                                    .padding(20.dp)
                                                    .weight(1f)
                                            )

                                            Spacer(modifier = Modifier.width(16.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}