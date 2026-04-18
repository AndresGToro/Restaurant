package com.andresdevs.restaurant.core.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.andresdevs.restaurant.presentation.categoria.CategoriaViewModel
import com.andresdevs.restaurant.presentation.main.AuthViewModel
import com.andresdevs.restaurant.presentation.producto.ProductoViewModel
import com.andresdevs.restaurant.presentation.usuario.UsuarioViewModel

fun authViewModelFactory(appContainer: AppContainer): ViewModelProvider.Factory =
    viewModelFactory {
        initializer {
            AuthViewModel(
                auth = appContainer.firebaseAuth,
                firestore = appContainer.firestore
            )
        }
    }

fun categoriaViewModelFactory(appContainer: AppContainer): ViewModelProvider.Factory =
    viewModelFactory {
        initializer {
            CategoriaViewModel(
                getCategoriasUseCase = appContainer.getCategoriaUseCase,
                createCategoriaUseCase = appContainer.createCategoriaUseCase,
                updateCategoriaUseCase = appContainer.updateCategoriaUseCase,
                deleteCategoriaUseCase = appContainer.deleteCategoriaUseCase
            )
        }
    }

fun productoViewModelFactory(appContainer: AppContainer): ViewModelProvider.Factory =
    viewModelFactory {
        initializer {
            ProductoViewModel(
                getProductosUseCase = appContainer.getProductoUseCase,
                createProductoUseCase = appContainer.createProductoUseCase,
                updateProductoUseCase = appContainer.updateProductoUseCase,
                deleteProductoUseCase = appContainer.deleteProductoUseCase
            )
        }
    }

fun usuarioViewModelFactory(appContainer: AppContainer): ViewModelProvider.Factory =
    viewModelFactory {
        initializer {
            UsuarioViewModel(
                getUsuarioUseCase = appContainer.getUsuarioUseCase,
                createUsuarioUseCase = appContainer.createUsuarioUseCase,
                updateUsuarioUseCase = appContainer.updateUsuarioUseCase,
                deleteUsuarioUseCase = appContainer.deleteUsuarioUseCase
            )
        }
    }
