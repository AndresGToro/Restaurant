package com.andresdevs.restaurant.datos

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andresdevs.restaurant.modulo.CategoriaUpdate
import com.andresdevs.restaurant.metodos.removeAccents
import com.andresdevs.restaurant.metodos.tituloNegro
import com.andresdevs.restaurant.modulo.Producto
import com.andresdevs.restaurant.modulo.ProductosVisualizar
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

data class CategoriaItems(
    val codeCategoria: String,
    val name: String,
    val url: String,
    val estado: String,
)

//OBTENER DATOS FIREBASE
suspend fun getCategoriaItems2(): List<CategoriaItems> {
    return suspendCoroutine { continuation ->
        val firebaseDatabase = Firebase.database

        //quito las tildes
        val textoConTilde = "Categoría"
        val textoSinTilde = textoConTilde.removeAccents()
        val myRef = firebaseDatabase.getReference(textoSinTilde)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<CategoriaItems>()
                for (childSnapshot in snapshot.children) {
                    val codeCategoria =
                        childSnapshot.child("codeCategoria").getValue(String::class.java)
                    val name = childSnapshot.child("name").getValue(String::class.java)
                    val url = childSnapshot.child("url").getValue(String::class.java)
                    val estado = childSnapshot.child("estado").getValue(String::class.java)
                    if (codeCategoria != null && name != null && url != null && estado != null) {
                        items.add(CategoriaItems(codeCategoria, name, url, estado))
                    }
                }
                // Devolvemos la lista directamente
                continuation.resume(items)
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error
            }
        })
    }
}

//DATOS ASINCRONICOS CARGAR EN LISTA
@Composable
fun getCategoriaItems(): List<CategoriaItems> {
    var items by remember { mutableStateOf(emptyList<CategoriaItems>()) }

    LaunchedEffect(Unit) {
        val itemList = getCategoriaItems2()
        items = itemList
    }
    return items
}   

//LEER (VISUALIZAR LISTA)
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun categoriaItemList(itemList: List<CategoriaItems>) {
    val context = LocalContext.current
    val deletedItem = remember { mutableStateListOf<CategoriaItems>() }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            tituloNegro("Categoría")
        }
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
                                    .background(Color.White)
                                    .clickable {
                                        // Acciones al hacer clic en la tarjeta
                                        val intent = Intent(context, Producto::class.java)
                                        intent.putExtra("codigoUnicoFilaCategoria", item.codeCategoria)
                                        context.startActivity(intent)
                                    },
                                shape = RoundedCornerShape(5.dp)
                            ) {
                                // Contenido de la Card
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        GlideImage(
                                            model = item.url,
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
                                                text = item.name,
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

                                            // Update
                                            IconButton(onClick = {
                                                val intent = Intent(context, CategoriaUpdate::class.java)
                                                intent.putExtra("codigoUnicoFilaCategoria", item.codeCategoria)
                                                intent.putExtra("nombreCategoria", item.name)
                                                intent.putExtra("urlImagen", item.url)
                                                context.startActivity(intent)
                                            }) {
                                                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Update")
                                            }

                                            Spacer(modifier = Modifier.width(16.dp))

                                            // Delete
                                            IconButton(onClick = {
                                                deletedItem.add(item)
                                                deleteCategoriaItem(item)
                                            }) {
                                                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Deletion")
                                            }
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

// ACTUALIZAR
fun updateCategoriaItem(codeCategoria: String, updatedName: String, updatedUrl: String, updateEstado: String) {
    // Utiliza el path adecuado en tu base de datos
    //quito las tildes
    val textoConTilde = "Categoría"
    val textoSinTilde = textoConTilde.removeAccents()
    val databaseReference = Firebase.database.getReference("$textoSinTilde/$codeCategoria")
    val updatedItem = CategoriaItems(codeCategoria, updatedName, updatedUrl, updateEstado)
    databaseReference.setValue(updatedItem)
        .addOnSuccessListener { }
        .addOnFailureListener { }
}

//ELIMINAR
fun deleteCategoriaItem(item: CategoriaItems) {
    //quito las tildes
    val textoConTilde = "Categoría"
    val textoSinTilde = textoConTilde.removeAccents()
    val databaseReference = Firebase.database.getReference(textoSinTilde)
    // Utiliza el método removeValue para eliminar el elemento de la base de datos
    databaseReference.child(item.codeCategoria).removeValue()
        .addOnSuccessListener { }
        .addOnFailureListener { }
}

// lista de forma horizontal
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun categoriaItemListHorizontal(itemList: List<CategoriaItems>) {
    val context = LocalContext.current
    val deletedItem = remember { mutableStateListOf<CategoriaItems>() }

    Row(
        modifier = Modifier.fillMaxWidth()
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
                                .background(Transparent)
                            .clickable {
                            // Acciones al hacer clic en la tarjeta
                            val intent = Intent(context, ProductosVisualizar::class.java)
                            intent.putExtra("codigoUnicoFilaCategoria", item.codeCategoria)
                            context.startActivity(intent)
                        },
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                GlideImage(
                                    model = item.url,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp) // Altura de la imagen, puedes ajustarlo según tus necesidades
                                        .clip(RoundedCornerShape(5.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre la imagen y el texto
                                Text(
                                    text = item.name,
                                    style = TextStyle(
                                        color = Black,
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight() // Permitir que el texto tenga su propio tamaño de altura
                                        .padding(8.dp)
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}