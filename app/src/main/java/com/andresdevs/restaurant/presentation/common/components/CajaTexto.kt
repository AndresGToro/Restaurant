package com.andresdevs.restaurant.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.andresdevs.restaurant.R

//=============================    CAJAS TEXTO O INGRESAR DATOS   =============================
@Composable
fun cajaTextoFondo(info: String): String {
    var text by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(info) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(color = Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
    }
    return text
}

@Composable
fun cajaTexto(titulo: String): String {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(titulo) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(color = Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
    }
    return text
}

//////////////// ESTOY MODIFICANDO, SI FUNCIONA REPETIR EN LOS DEMAS METODOS
@Composable
fun cajaTexto2(titulo: String, info: String): String {
    var text by remember { mutableStateOf(info) }

    LaunchedEffect(info) { text = info }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { newText -> text = newText },
            label = { Text(titulo) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(color = Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
    }
    return text
}

@Composable
fun contrasenaFondo(info: String): String {
    var text by remember {
        mutableStateOf("")
    }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(info) },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            trailingIcon = {
                Icon(painter = if (isPasswordVisible)
                    painterResource(id = R.drawable.icon_password_of)
                else painterResource(id = R.drawable.icon_password),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { isPasswordVisible = !isPasswordVisible })
            }
        )
    }
    return text
}


@Composable
fun contrasena(info: String): String {
    var text by remember {
        mutableStateOf("")
    }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(info) },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            trailingIcon = {
                Icon(painter = if (isPasswordVisible)
                    painterResource(id = R.drawable.icon_password_of)
                else painterResource(id = R.drawable.icon_password),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { isPasswordVisible = !isPasswordVisible })
            }
        )
    }
    return text
}

@Composable
fun cajaNumerosEnteros(info: String): String {
    var text by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                // Ensure that the entered text consists of only numeric characters
                if (it.all { char -> char.isDigit() }) {
                    text = it
                }
            },
            label = { Text(info) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(color = Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
    }
    return text
}

@Composable
fun cajaNumerosDecimales(titulo: String, info: String): String {
    var text by remember { mutableStateOf(info) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                // Ensure that the entered text consists of only valid numeric characters
                if (it.matches(Regex("^\\d*(\\.\\d{0,2})?\$"))) {
                    text = it
                }
            },
            label = { Text(titulo) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(color = Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
    }
    return text
}