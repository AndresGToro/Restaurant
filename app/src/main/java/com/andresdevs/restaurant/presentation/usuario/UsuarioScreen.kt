package com.andresdevs.restaurant.presentation.usuario

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andresdevs.restaurant.RestaurantApp
import com.andresdevs.restaurant.core.di.usuarioViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuarioScreen(
    onLogout: () -> Unit = {}
) {
    val appContainer = (LocalContext.current.applicationContext as RestaurantApp).appContainer
    val viewModel: UsuarioViewModel = viewModel(
        factory = usuarioViewModelFactory(appContainer)
    )
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Usuarios") },
                actions = {
                    TextButton(
                        onClick = onLogout
                    ) {
                        Text("Cerrar sesion")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar usuario")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                if (state.isLoading) {
                    Text("Cargando...", color = MaterialTheme.colorScheme.primary)
                }
                if (!state.error.isNullOrBlank()) {
                    Text(
                        text = state.error.orEmpty(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            items(state.usuarios) { usuario ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(usuario.nombreCompleto)
                            Text(usuario.correo)
                            Text("Cargo: ${usuario.cargo}")
                        }
                        IconButton(onClick = { viewModel.eliminarUsuario(usuario.cedula) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddUsuarioDialog(
            state = state,
            onCedula = viewModel::onCedulaChange,
            onNombre = viewModel::onNombreCompletoChange,
            onUsuario = viewModel::onUsuarioChange,
            onCargo = viewModel::onCargoChange,
            onCelular = viewModel::onCelularChange,
            onCorreo = viewModel::onCorreoChange,
            onContrasena = viewModel::onContrasenaChange,
            onDireccion = viewModel::onDireccionChange,
            onDismiss = { showDialog = false },
            onConfirm = {
                viewModel.guardarUsuario()
                showDialog = false
            }
        )
    }
}

@Composable
private fun AddUsuarioDialog(
    state: UsuarioState,
    onCedula: (String) -> Unit,
    onNombre: (String) -> Unit,
    onUsuario: (String) -> Unit,
    onCargo: (String) -> Unit,
    onCelular: (String) -> Unit,
    onCorreo: (String) -> Unit,
    onContrasena: (String) -> Unit,
    onDireccion: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo usuario") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(value = state.cedula, onValueChange = onCedula, label = { Text("Cedula") })
                OutlinedTextField(value = state.nombreCompleto, onValueChange = onNombre, label = { Text("Nombre") })
                OutlinedTextField(value = state.usuario, onValueChange = onUsuario, label = { Text("Usuario") })
                OutlinedTextField(value = state.cargo, onValueChange = onCargo, label = { Text("Cargo") })
                OutlinedTextField(value = state.celular, onValueChange = onCelular, label = { Text("Celular") })
                OutlinedTextField(value = state.correo, onValueChange = onCorreo, label = { Text("Correo") })
                OutlinedTextField(value = state.contrasena, onValueChange = onContrasena, label = { Text("Contrasena") })
                OutlinedTextField(value = state.direccion, onValueChange = onDireccion, label = { Text("Direccion") })
                Spacer(modifier = Modifier.height(8.dp))
            }
        },
        confirmButton = { Button(onClick = onConfirm) { Text("Guardar") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}
