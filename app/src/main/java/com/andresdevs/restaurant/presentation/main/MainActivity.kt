package com.andresdevs.restaurant.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andresdevs.restaurant.R
import com.andresdevs.restaurant.RestaurantApp
import com.andresdevs.restaurant.core.di.authViewModelFactory
import com.andresdevs.restaurant.domain.model.UserRole
import com.andresdevs.restaurant.presentation.navigation.bottomnav.PantallaPrincipal
import com.andresdevs.restaurant.ui.theme.RestaurantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantTheme {
                LoginRoute(
                    onLoginSuccess = { role ->
                        startActivity(
                            Intent(this, PantallaPrincipal::class.java).apply {
                                putExtra("user_role", role.name)
                            }
                        )
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
private fun LoginRoute(
    onLoginSuccess: (UserRole) -> Unit,
) {
    val appContainer = (LocalContext.current.applicationContext as RestaurantApp).appContainer
    val viewModel: AuthViewModel = viewModel(
        factory = authViewModelFactory(appContainer)
    )
    val state by viewModel.state.collectAsStateWithLifecycle()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state.isAuthenticated, state.role) {
        val role = state.role
        if (state.isAuthenticated && role != null) {
            onLoginSuccess(role)
        }
    }

    LoginScreen(
        email = email,
        password = password,
        isLoading = state.isLoading,
        error = state.error,
        message = state.message,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onLogin = { viewModel.login(email.trim(), password) },
        onRegister = { viewModel.register(email.trim(), password) },
        onResetPassword = { viewModel.resetPassword(email.trim()) }
    )
}

@Composable
private fun LoginScreen(
    email: String,
    password: String,
    isLoading: Boolean,
    error: String?,
    message: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    onResetPassword: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.1f)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Alitas BBQ",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                singleLine = true,
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                singleLine = true,
                label = { Text("Contrasena") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onLogin,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text("Iniciar sesion")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onRegister,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onResetPassword,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Recuperar contraseña")
            }

            if (!error.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }
            if (!message.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message,
                    color = Color.White
                )
            }
        }
    }
}
