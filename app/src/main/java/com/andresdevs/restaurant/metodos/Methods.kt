package com.andresdevs.restaurant.metodos

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andresdevs.restaurant.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

//=============================    BOTONES    =============================
@Composable
fun botonCRUD(texto: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Button(
            onClick = { onClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(texto)
        }
    }
}

@Composable
fun botonFlotanteAgregar(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier
            .padding(16.dp)
            .size(70.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null
        )
    }
}


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

//=============================    TEXTO VISUALIZAR O LEER   =============================
@Composable
fun tituloBlanco(titulo: String): String {
    Text(
        text = titulo,
        color = Color.White,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
    return titulo
}

@Composable
fun tituloNegro(titulo: String): String {

    Text(
        text = titulo,
        color = Black,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
    return titulo
}

@Composable
fun textoColorAlitas(text: String): String {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2,
        color = contentColorFor(backgroundColor = Color(0xff8e2301))
    )
    return text
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun menuBox() {
    val context = LocalContext.current
    val coffeeDrinks =
        arrayOf("Administrador", "Mesero", "Chef")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                label = { Text(text = "Cargo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
            )

            val filteredOptions =
                coffeeDrinks.filter { it.contains(selectedText, ignoreCase = true) }
            if (filteredOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        // We shouldn't hide the menu when the user enters/removes any character
                    }
                ) {
                    filteredOptions.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedText = item
                                expanded = false
                                Toast.makeText(context, item, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun estado(): String {
    val context = LocalContext.current
    val coffeeDrinks = arrayOf("Activo", "Inactivo")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                label = { Text(text = "Estado") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
            )

            val filteredOptions =
                coffeeDrinks.filter { it.contains(selectedText, ignoreCase = true) }
            if (filteredOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        // No ocultar el menú cuando el usuario ingresa/quita cualquier carácter
                    }
                ) {
                    filteredOptions.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedText = item
                                expanded = false
                                Toast.makeText(context, item, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        )
                    }
                }
            }
        }
    }

    // Devuelve el texto seleccionado
    return selectedText
}


//quitar tildes
fun String.removeAccents(): String {
    val unaccentedString = StringBuilder()
    for (char in this) {
        val unaccentedChar = when (char) {
            'á', 'à', 'â', 'ä' -> 'a'
            'é', 'è', 'ê', 'ë' -> 'e'
            'í', 'ì', 'î', 'ï' -> 'i'
            'ó', 'ò', 'ô', 'ö' -> 'o'
            'ú', 'ù', 'û', 'ü' -> 'u'
            'ñ' -> 'n'
            'ç' -> 'c'
            else -> char
        }
        unaccentedString.append(unaccentedChar)
    }
    return unaccentedString.toString()
}

