package com.andresdevs.restaurant.datos

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andresdevs.restaurant.metodos.removeAccents
import com.andresdevs.restaurant.metodos.tituloNegro
import com.andresdevs.restaurant.modulo.ProductoUpdate
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

data class ProductoItems(
    val codeCategoriaProducto: String,
    val codeProducto: String,
    val nameProducto: String,
    val precioProducto: String,
    val urlProducto: String,
    val estadoProducto: String,
)

//OBTENER DATOS FIREBASE
suspend fun getProductoItems2(): List<ProductoItems> {
    return suspendCoroutine { continuation ->
        val firebaseDatabase = Firebase.database

        //quito las tildes
        val textoConTilde = "Producto"
        val textoSinTilde = textoConTilde.removeAccents()
        val myRef = firebaseDatabase.getReference(textoSinTilde)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<ProductoItems>()
                for (childSnapshot in snapshot.children) {
                    val codeCategoriaProducto =
                        childSnapshot.child("codeCategoriaProducto").getValue(String::class.java)
                    val codeProducto =
                        childSnapshot.child("codeProducto").getValue(String::class.java)
                    val nameProducto =
                        childSnapshot.child("nameProducto").getValue(String::class.java)
                    val precioProducto =
                        childSnapshot.child("precioProducto").getValue(String::class.java)
                    val urlProducto =
                        childSnapshot.child("urlProducto").getValue(String::class.java)
                    val estadoProducto =
                        childSnapshot.child("estadoProducto").getValue(String::class.java)
                    if (codeCategoriaProducto != null && codeProducto != null && nameProducto != null && precioProducto != null && urlProducto != null && estadoProducto != null) {
                        items.add(
                            ProductoItems(
                                codeCategoriaProducto,
                                codeProducto,
                                nameProducto,
                                precioProducto,
                                urlProducto,
                                estadoProducto,
                            )
                        )
                        Log.e(
                            TAG,
                            "Funciona $codeCategoriaProducto $nameProducto $precioProducto $urlProducto"
                        )
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
fun getProductoItems(): List<ProductoItems> {
    Log.e(TAG, "Prueba")
    var items by remember { mutableStateOf(emptyList<ProductoItems>()) }

    LaunchedEffect(Unit) {
        val itemList = getProductoItems2()
        items = itemList
    }
    return items
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun productoItemList(itemList: List<ProductoItems>, codigoCategoria: String) {
    val context = LocalContext.current
    val deletedItem = remember { mutableStateListOf<ProductoItems>() }

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
            tituloNegro("Producto")
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
                            if (item.codeCategoriaProducto == codigoCategoria) {
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

                                                // Update
                                                IconButton(onClick = {
                                                    val intent =
                                                        Intent(context, ProductoUpdate::class.java)
                                                    intent.putExtra(
                                                        "codeCategoriaProducto",
                                                        item.codeCategoriaProducto
                                                    )
                                                    intent.putExtra(
                                                        "codeProducto",
                                                        item.codeProducto
                                                    )
                                                    intent.putExtra(
                                                        "nameProducto",
                                                        item.nameProducto
                                                    )
                                                    intent.putExtra(
                                                        "precioProducto",
                                                        item.precioProducto
                                                    )
                                                    intent.putExtra("urlProducto", item.urlProducto)
                                                    intent.putExtra(
                                                        "estadoProducto",
                                                        item.estadoProducto
                                                    )
                                                    context.startActivity(intent)
                                                }) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Edit,
                                                        contentDescription = "Update"
                                                    )
                                                }

                                                Spacer(modifier = Modifier.width(16.dp))

                                                // Delete
                                                IconButton(onClick = {
                                                    val itemToDelete =
                                                        item // Guardar el valor actual de item
                                                    // Eliminar el item guardado
                                                    confirmar(context) {
                                                        deleteProductoItem(itemToDelete)
                                                    }
                                                }) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Delete,
                                                        contentDescription = "Deletion"
                                                    )
                                                }
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


//Filtrar solo promos
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun productoPromoItemList(itemList: List<ProductoItems>, codigoCategoria: String) {
    val deletedItem = remember { mutableStateListOf<ProductoItems>() }

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
            tituloNegro("Promos")
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
                            if (item.codeCategoriaProducto == codigoCategoria) {
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
                    }
                )
            }
        }
    }
}

fun confirmar(context: Context, onConfirm: () -> Unit) {
    AlertDialog.Builder(context)
        .setTitle("Eliminar")
        .setMessage("Seguro desea eliminar?")
        .setPositiveButton(android.R.string.ok) { dialog, which ->
            //botón OK pulsado
            onConfirm.invoke()
        }
        .setNegativeButton(android.R.string.cancel) { dialog, which ->
            //botón cancel pulsado
        }
        .show()
}


// ACTUALIZAR
fun updateProductoItem(
    codeCategoriaProducto: String,
    codeProducto: String,
    nameProducto: String,
    precioProducto: String,
    urlProducto: String,
    estadoProducto: String,
) {
    // Utiliza el path adecuado en tu base de datos
    val databaseReference = Firebase.database.getReference("Producto/$codeProducto")
    val updatedItem2 = ProductoItems(
        codeCategoriaProducto,
        codeProducto,
        nameProducto,
        precioProducto,
        urlProducto,
        estadoProducto
    )
    databaseReference.setValue(updatedItem2)
        .addOnSuccessListener { }
        .addOnFailureListener { }
}

//ELIMINAR
fun deleteProductoItem(item: ProductoItems) {
    val databaseReference = Firebase.database.getReference("Producto")
    // Utiliza el método removeValue para eliminar el elemento de la base de datos
    databaseReference.child(item.codeProducto).removeValue()
        .addOnSuccessListener { }
        .addOnFailureListener { }
}


// productos Visualizar mesero
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun productoItemListMesero(itemList: List<ProductoItems>, codigoCategoria: String) {
    val deletedItem = remember { mutableStateListOf<ProductoItems>() }
    val context = LocalContext.current

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
            tituloNegro("Producto")
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
                            if (item.codeCategoriaProducto == codigoCategoria) {
                                // La función Card y clickable están dentro de un contexto composable
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .padding(10.dp, 5.dp, 10.dp, 5.dp)
                                        .background(Color.White)
                                        .clickable {
                                            //add click

                                            Toast.makeText(
                                                context,
                                                "Producto agregado !!!",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        },

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
