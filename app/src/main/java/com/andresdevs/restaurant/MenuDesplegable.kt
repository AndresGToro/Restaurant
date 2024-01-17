package com.andresdevs.restaurant

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andresdevs.restaurant.datos.getCategoriaItems
import com.andresdevs.restaurant.metodos.botonCRUD
import com.andresdevs.restaurant.metodos.botonFlotanteAgregar
import com.andresdevs.restaurant.metodos.cajaNumerosEnteros
import com.andresdevs.restaurant.metodos.tituloNegro
import kotlinx.coroutines.launch

//=============================  MENU DESPLEGABLE   =============================

data class DrawerItems(
    val icon: ImageVector,
    val text: String,
    val badgeCount: Int,
    val hasBadge: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer() {
    val drawerItem = listOf(
        DrawerItems(Icons.Default.Home, "Página principal", 0, false),
        DrawerItems(Icons.Default.ShoppingCart, "Menú", 0, false),
        DrawerItems(Icons.Default.AccountCircle, "Usuario", 0, false),
        DrawerItems(Icons.Default.AddCircle, "Gestionar categoría", 0, false),
        DrawerItems(Icons.Default.AddCircle, "Gestionar productos", 0, false),
        DrawerItems(Icons.Default.List, "Lista", 0, false),
        DrawerItems(Icons.Default.Build, "Iva", 0, false)
    )
    val drawerItem2 = listOf(
        DrawerItems(Icons.Default.ExitToApp, "Cerrar sesión", 0, false)
    )
    var selectedItem by remember {
        mutableStateOf(drawerItem[0])
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {

            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(0.dp)) {

                drawerHeader()

                drawerItem.forEach {
                    NavigationDrawerItem(label = { Text(text = it.text) },
                        selected = it == selectedItem,
                        onClick = {
                            selectedItem = it
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                        icon = {
                            Icon(imageVector = it.icon, contentDescription = it.text)
                        },
                        badge = {
                            if (it.hasBadge) {
                                BadgedBox(badge = {
                                    Badge {
                                        Text(text = it.badgeCount.toString(), fontSize = 17.sp)
                                    }
                                }) {

                                }
                            }
                        }
                    )
                }

                drawerDivider()

                drawerItem2.forEach {
                    NavigationDrawerItem(label = { Text(text = it.text) },
                        selected = it == selectedItem,
                        onClick = {
                            selectedItem = it

                            scope.launch {
                                drawerState.close()
                            }

                        },
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                        icon = {
                            Icon(imageVector = it.icon, contentDescription = it.text)
                        }
                    )
                }
            }
        }
    }, drawerState = drawerState) {

        Scaffold(topBar = {
            TopAppBar(title = { Text(text = "Restaurant") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu Icon")
                    }

                }
            )
        }) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                //TODOS BOTONES VISUALIZAN PANTALLA
                menuEstatico()
            }

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer2() {
    val context = LocalContext.current
    val drawerItem = listOf(
        DrawerItems(Icons.Default.Home, "Página principal", 0, false),
        DrawerItems(Icons.Default.ShoppingCart, "Menú", 0, false),
        DrawerItems(Icons.Default.AccountCircle, "Usuario", 0, false),
        DrawerItems(Icons.Default.AddCircle, "Gestionar categoría", 0, false),
        DrawerItems(Icons.Default.AddCircle, "Gestionar productos", 0, false),
        DrawerItems(Icons.Default.List, "Lista", 0, false),
        DrawerItems(Icons.Default.Build, "Iva", 0, false)
    )
    val drawerItem2 = listOf(
        DrawerItems(Icons.Default.ExitToApp, "Cerrar sesión", 0, false)
    )
    var selectedItem by remember {
        mutableStateOf(drawerItem[0])
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {

            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(0.dp)) {

                drawerHeader()

                drawerItem.forEach {
                    NavigationDrawerItem(label = { Text(text = it.text) },
                        selected = it == selectedItem,
                        onClick = {
                            selectedItem = it
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                        icon = {
                            Icon(imageVector = it.icon, contentDescription = it.text)
                        },
                        badge = {
                            if (it.hasBadge) {
                                BadgedBox(badge = {
                                    Badge {
                                        Text(text = it.badgeCount.toString(), fontSize = 17.sp)
                                    }
                                }) {

                                }
                            }
                        }
                    )
                }

                drawerDivider()

                drawerItem2.forEach {
                    NavigationDrawerItem(label = { Text(text = it.text) },
                        selected = it == selectedItem,
                        onClick = {
                            selectedItem = it

                            scope.launch {
                                drawerState.close()
                            }

                        },
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                        icon = {
                            Icon(imageVector = it.icon, contentDescription = it.text)
                        }
                    )
                }
            }
        }
    }, drawerState = drawerState) {

        Scaffold(topBar = {
            TopAppBar(title = { Text(text = "Restaurant") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu Icon")
                    }

                }
            )
        }) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.BottomEnd
            ) {
                //TODOS BOTONES VISUALIZAN PANTALLA
                botonFlotanteAgregar(
                    onClick = {
                        context.startActivity(Intent(context, UsuarioCreate::class.java))
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer3() {
    val drawerItem = listOf(
        DrawerItems(Icons.Default.Home, "Página principal", 0, false),
        DrawerItems(Icons.Default.ShoppingCart, "Menú", 0, false),
        DrawerItems(Icons.Default.AccountCircle, "Usuario", 0, false),
        DrawerItems(Icons.Default.AddCircle, "Gestionar categoría", 0, false),
        DrawerItems(Icons.Default.AddCircle, "Gestionar productos", 0, false),
        DrawerItems(Icons.Default.List, "Lista", 0, false),
        DrawerItems(Icons.Default.Build, "Iva", 0, false)
    )
    val drawerItem2 = listOf(
        DrawerItems(Icons.Default.ExitToApp, "Cerrar sesión", 0, false)
    )
    var selectedItem by remember {
        mutableStateOf(drawerItem[0])
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {

            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(0.dp)) {

                drawerHeader()

                drawerItem.forEach {
                    NavigationDrawerItem(label = { Text(text = it.text) },
                        selected = it == selectedItem,
                        onClick = {
                            selectedItem = it
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                        icon = {
                            Icon(imageVector = it.icon, contentDescription = it.text)
                        },
                        badge = {
                            if (it.hasBadge) {
                                BadgedBox(badge = {
                                    Badge {
                                        Text(text = it.badgeCount.toString(), fontSize = 17.sp)
                                    }
                                }) {

                                }
                            }
                        }
                    )
                }

                drawerDivider()

                drawerItem2.forEach {
                    NavigationDrawerItem(label = { Text(text = it.text) },
                        selected = it == selectedItem,
                        onClick = {
                            selectedItem = it

                            scope.launch {
                                drawerState.close()
                            }

                        },
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                        icon = {
                            Icon(imageVector = it.icon, contentDescription = it.text)
                        }
                    )
                }
            }
        }
    }, drawerState = drawerState) {

        Scaffold(topBar = {
            TopAppBar(title = { Text(text = "Restaurant") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu Icon")
                    }

                }
            )
        }) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    tituloNegro("Iva")
                    cajaNumerosEnteros("Iva")
                    botonCRUD("Actualizar") {

                    }
                }
            }
        }
    }
}

//CATEGORIA
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer4() {
    val context = LocalContext.current

    val drawerItem = listOf(
        DrawerItems(Icons.Default.Home, "Página principal", 0, false),
        DrawerItems(Icons.Default.ShoppingCart, "Menú", 0, false),
        DrawerItems(Icons.Default.AccountCircle, "Usuario", 0, false),
        DrawerItems(Icons.Default.AddCircle, "Gestionar categoría", 0, false),
        DrawerItems(Icons.Default.AddCircle, "Gestionar productos", 0, false),
        DrawerItems(Icons.Default.List, "Lista", 0, false),
        DrawerItems(Icons.Default.Build, "Iva", 0, false)
    )
    val drawerItem2 = listOf(
        DrawerItems(Icons.Default.ExitToApp, "Cerrar sesión", 0, false)
    )
    var selectedItem by remember {
        mutableStateOf(drawerItem[0])
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {

            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(0.dp)) {

                drawerHeader()

                drawerItem.forEach {
                    NavigationDrawerItem(label = { Text(text = it.text) },
                        selected = it == selectedItem,
                        onClick = {
                            selectedItem = it
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                        icon = {
                            Icon(imageVector = it.icon, contentDescription = it.text)
                        },
                        badge = {
                            if (it.hasBadge) {
                                BadgedBox(badge = {
                                    Badge {
                                        Text(text = it.badgeCount.toString(), fontSize = 17.sp)
                                    }
                                }) {

                                }
                            }
                        }
                    )
                }

                drawerDivider()

                drawerItem2.forEach {
                    NavigationDrawerItem(label = { Text(text = it.text) },
                        selected = it == selectedItem,
                        onClick = {
                            selectedItem = it

                            scope.launch {
                                drawerState.close()
                            }

                        },
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                        icon = {
                            Icon(imageVector = it.icon, contentDescription = it.text)
                        }
                    )
                }
            }
        }
    }, drawerState = drawerState) {

        Scaffold(topBar = {
            TopAppBar(title = { Text(text = "Restaurant") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu Icon")
                    }

                }
            )
        }) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.BottomEnd
            ) {
                //TODOS BOTONES VISUALIZAN PANTALLA
                categoriaItemList(itemList = getCategoriaItems())
                //BTOTON AGREGAR PANTALLA
                botonFlotanteAgregar(
                    onClick = {
                        context.startActivity(Intent(context, CategoriaCreate::class.java))
                    }
                )
            }
        }
    }
}

//PRODUCTOS
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer5() {
    val context = LocalContext.current

    val drawerItem = listOf(
        DrawerItems(Icons.Default.Home, "Página principal", 0, false),
        DrawerItems(Icons.Default.ShoppingCart, "Menú", 0, false),
        DrawerItems(Icons.Default.AccountCircle, "Usuario", 0, false),
        DrawerItems(Icons.Default.AddCircle, "Gestionar categoría", 0, false),
        DrawerItems(Icons.Default.AddCircle, "Gestionar productos", 0, false),
        DrawerItems(Icons.Default.List, "Lista", 0, false),
        DrawerItems(Icons.Default.Build, "Iva", 0, false)
    )
    val drawerItem2 = listOf(
        DrawerItems(Icons.Default.ExitToApp, "Cerrar sesión", 0, false)
    )
    var selectedItem by remember {
        mutableStateOf(drawerItem[0])
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {

            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(0.dp)) {

                drawerHeader()

                drawerItem.forEach {
                    NavigationDrawerItem(label = { Text(text = it.text) },
                        selected = it == selectedItem,
                        onClick = {
                            selectedItem = it
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                        icon = {
                            Icon(imageVector = it.icon, contentDescription = it.text)
                        },
                        badge = {
                            if (it.hasBadge) {
                                BadgedBox(badge = {
                                    Badge {
                                        Text(text = it.badgeCount.toString(), fontSize = 17.sp)
                                    }
                                }) {

                                }
                            }
                        }
                    )
                }

                drawerDivider()

                drawerItem2.forEach {
                    NavigationDrawerItem(label = { Text(text = it.text) },
                        selected = it == selectedItem,
                        onClick = {
                            selectedItem = it

                            scope.launch {
                                drawerState.close()
                            }

                        },
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                        icon = {
                            Icon(imageVector = it.icon, contentDescription = it.text)
                        }
                    )
                }
            }
        }
    }, drawerState = drawerState) {

        Scaffold(topBar = {
            TopAppBar(title = { Text(text = "Restaurant") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu Icon")
                    }

                }
            )
        }) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.BottomEnd
            ) {
                //TODOS BOTONES VISUALIZAN PANTALLA

                botonFlotanteAgregar(
                    onClick = {
                        context.startActivity(Intent(context, ProductoCreate::class.java))
                    }
                )
            }
        }
    }
}

@Composable
fun drawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xff8e2301)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.personaldevelopment),
                contentDescription = "profile pic",
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "Andrés Garcés Toro",
                color = Color.White,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
        Divider(
            Modifier.align(Alignment.BottomCenter), thickness = 1.dp,
            Color.DarkGray
        )
    }
}

@Composable
private fun drawerDivider() {
    Divider(thickness = 1.dp, color = Color.DarkGray)
}