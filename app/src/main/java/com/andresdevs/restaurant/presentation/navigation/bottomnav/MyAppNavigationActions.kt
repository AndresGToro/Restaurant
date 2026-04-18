package com.andresdevs.restaurant.presentation.navigation.bottomnav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.andresdevs.restaurant.domain.model.UserRole
import com.andresdevs.restaurant.R

class MyAppNavigationActions (private val navController: NavHostController) {
    fun navigateTo(destination: MyAppTopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }
    
    fun navigateToProductos(categoriaId: String) {
        navController.navigate("${MyAppRoute.PRODUCTS}/$categoriaId")
    }
}

data class MyAppTopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val iconTextId: Int
)

val TOP_LEVEL_DESTINATIONS = listOf(
    MyAppTopLevelDestination(
        route = MyAppRoute.HOME,
        selectedIcon = Icons.Default.Home,
        iconTextId = R.string.home
    ),
    MyAppTopLevelDestination(
        route = MyAppRoute.CATEGORIES,
        selectedIcon = Icons.Default.Category,
        iconTextId = R.string.categories
    ),
    MyAppTopLevelDestination(
        route = MyAppRoute.CART,
        selectedIcon = Icons.Default.ShoppingCart,
        iconTextId = R.string.cart
    ),
    MyAppTopLevelDestination(
        route = MyAppRoute.USERS,
        selectedIcon = Icons.Default.Person,
        iconTextId = R.string.users
    ),
)

fun topLevelDestinationsForRole(role: UserRole): List<MyAppTopLevelDestination> {
    return when (role) {
        UserRole.ADMIN -> TOP_LEVEL_DESTINATIONS
        UserRole.COCINA -> TOP_LEVEL_DESTINATIONS.filter {
            it.route == MyAppRoute.HOME ||
                it.route == MyAppRoute.CATEGORIES ||
                it.route == MyAppRoute.CART
        }
        UserRole.CAJERO -> TOP_LEVEL_DESTINATIONS.filter {
            it.route == MyAppRoute.HOME ||
                it.route == MyAppRoute.CART
        }
        UserRole.MESERO -> TOP_LEVEL_DESTINATIONS.filter {
            it.route == MyAppRoute.HOME ||
                it.route == MyAppRoute.CATEGORIES ||
                it.route == MyAppRoute.CART
        }
        UserRole.CLIENTE -> TOP_LEVEL_DESTINATIONS.filter {
            it.route == MyAppRoute.HOME ||
                it.route == MyAppRoute.CATEGORIES
        }
    }
}

object MyAppRoute {
    const val HOME = "home"
    const val CATEGORIES = "categories"
    const val PRODUCTS = "products"
    const val CART = "cart"
    const val USERS = "users"
}
