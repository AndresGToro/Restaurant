package com.andresdevs.restaurant.presentation.producto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andresdevs.restaurant.RestaurantApp
import com.andresdevs.restaurant.core.di.productoViewModelFactory
import com.andresdevs.restaurant.domain.model.Producto
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun ProductoScreen(
    codigoCategoria: String? = null
) {
    val appContainer = (LocalContext.current.applicationContext as RestaurantApp).appContainer
    val viewModel: ProductoViewModel = viewModel(
        factory = productoViewModelFactory(appContainer)
    )
    val state by viewModel.state.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedToEdit by remember { mutableStateOf<Producto?>(null) }

    LaunchedEffect(codigoCategoria) {
        codigoCategoria?.let { viewModel.updateCategoria(it) }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Productos") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar producto")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.error?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 8.dp)
                )
            }

            val productosFiltrados = if (codigoCategoria != null) {
                state.productos.filter { it.codeCategoriaProducto == codigoCategoria }
            } else {
                state.productos
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(productosFiltrados) { producto ->
                    ProductoItem(
                        producto = producto,
                        onEdit = { selectedToEdit = producto },
                        onDelete = { viewModel.eliminarProducto(producto) }
                    )
                }
            }

            if (showDialog) {
                AddProductoDialog(
                    title = "Nuevo producto",
                    name = state.nameProducto,
                    precio = state.precioProducto,
                    url = state.urlProducto,
                    onNameChange = viewModel::updateName,
                    onPrecioChange = viewModel::updatePrecio,
                    onUrlChange = viewModel::updateUrl,
                    onDismiss = { showDialog = false },
                    onConfirm = {
                        viewModel.guardarProducto()
                        showDialog = false
                    }
                )
            }

            selectedToEdit?.let { producto ->
                var editName by remember(producto.codeProducto) { mutableStateOf(producto.nameProducto) }
                var editPrecio by remember(producto.codeProducto) { mutableStateOf(producto.precioProducto) }
                var editUrl by remember(producto.codeProducto) { mutableStateOf(producto.urlProducto) }

                AddProductoDialog(
                    title = "Editar producto",
                    name = editName,
                    precio = editPrecio,
                    url = editUrl,
                    onNameChange = { editName = it },
                    onPrecioChange = { editPrecio = it },
                    onUrlChange = { editUrl = it },
                    onDismiss = { selectedToEdit = null },
                    onConfirm = {
                        viewModel.actualizarProducto(
                            producto = producto,
                            nuevoNombre = editName.trim(),
                            nuevoPrecio = editPrecio.trim(),
                            nuevaUrl = editUrl.trim()
                        )
                        selectedToEdit = null
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ProductoItem(
    producto: Producto,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = producto.urlProducto,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = producto.nameProducto, style = MaterialTheme.typography.titleMedium)
                Text(text = "$${producto.precioProducto}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun AddProductoDialog(
    title: String,
    name: String,
    precio: String,
    url: String,
    onNameChange: (String) -> Unit,
    onPrecioChange: (String) -> Unit,
    onUrlChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = precio,
                    onValueChange = onPrecioChange,
                    label = { Text("Precio") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = url,
                    onValueChange = onUrlChange,
                    label = { Text("URL imagen") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = { Button(onClick = onConfirm) { Text("Guardar") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}
