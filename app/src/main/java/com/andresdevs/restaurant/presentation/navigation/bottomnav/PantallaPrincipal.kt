package com.andresdevs.restaurant.presentation.navigation.bottomnav

import android.os.Bundle
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.andresdevs.restaurant.domain.model.UserRole
import com.andresdevs.restaurant.presentation.carrito.CarritoScreen
import com.andresdevs.restaurant.presentation.categoria.CategoriaScreen
import com.andresdevs.restaurant.presentation.home.HomeScreen
import com.andresdevs.restaurant.presentation.producto.ProductoScreen
import com.andresdevs.restaurant.presentation.main.MainActivity
import com.andresdevs.restaurant.presentation.usuario.UsuarioScreen
import com.andresdevs.restaurant.ui.theme.RestaurantTheme
import com.google.firebase.auth.FirebaseAuth

class PantallaPrincipal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantTheme {
                val roleName = intent.getStringExtra("user_role")
                val userRole = try {
                    UserRole.valueOf(roleName ?: UserRole.CLIENTE.name)
                } catch (_: IllegalArgumentException) {
                    UserRole.CLIENTE
                }
                val allowedDestinations = topLevelDestinationsForRole(userRole)
                val startRoute = allowedDestinations.firstOrNull()?.route ?: MyAppRoute.HOME
                val navController = rememberNavController()
                val navigateAction = remember(navController) {
                    MyAppNavigationActions(navController)
                }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val selectedDestination = navBackStackEntry?.destination?.route ?: startRoute

                MyAppContent(
                    startDestination = startRoute,
                    allowedDestinations = allowedDestinations,
                    navController = navController,
                    selectedDestination = selectedDestination,
                    navigateTopLevelDestination = navigateAction::navigateTo
                )
            }
        }
    }
}

@Composable
fun MyAppContent(
    modifier: Modifier = Modifier,
    startDestination: String,
    allowedDestinations: List<MyAppTopLevelDestination>,
    navController: NavHostController,
    selectedDestination: String,
    navigateTopLevelDestination: (MyAppTopLevelDestination) -> Unit
) {
    Row(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = navController,
                startDestination = startDestination
            ) {
                composable(MyAppRoute.HOME) {
                    HomeScreen()
                }
                if (allowedDestinations.any { it.route == MyAppRoute.CATEGORIES }) {
                    composable(MyAppRoute.CATEGORIES) {
                    CategoriaScreen(
                        onCategoriaClick = { categoriaId ->
                                val categoryDestination = allowedDestinations.firstOrNull {
                                    it.route == MyAppRoute.CATEGORIES
                                }
                                if (categoryDestination != null) {
                                    navigateTopLevelDestination(categoryDestination)
                                }
                            navController.navigate("${MyAppRoute.PRODUCTS}/$categoriaId")
                        }
                    )
                }
                }
                composable("${MyAppRoute.PRODUCTS}/{categoriaId}") { backStackEntry ->
                    val categoriaId = backStackEntry.arguments?.getString("categoriaId")
                    ProductoScreen(codigoCategoria = categoriaId)
                }
                if (allowedDestinations.any { it.route == MyAppRoute.CART }) {
                    composable(MyAppRoute.CART) {
                    CarritoScreen()
                }
                }
                if (allowedDestinations.any { it.route == MyAppRoute.USERS }) {
                    composable(MyAppRoute.USERS) {
                    val context = LocalContext.current
                    UsuarioScreen(
                        onLogout = {
                            FirebaseAuth.getInstance().signOut()
                            context.startActivity(
                                Intent(context, MainActivity::class.java).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                }
                            )
                        }
                    )
                }
                }
            }
            MyAppBottomNavigation(
                destinations = allowedDestinations,
                selectedDestination = selectedDestination,
                navigateTopLevelDestination = navigateTopLevelDestination
            )
        }
    }
}

@Composable
fun MyAppBottomNavigation(
    destinations: List<MyAppTopLevelDestination>,
    selectedDestination: String,
    navigateTopLevelDestination: (MyAppTopLevelDestination) -> Unit
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        destinations.forEach { destination ->
            NavigationBarItem(
                selected = selectedDestination == destination.route,
                onClick = { navigateTopLevelDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = stringResource(id = destination.iconTextId)
                    )
                }
            )
        }
    }
}
