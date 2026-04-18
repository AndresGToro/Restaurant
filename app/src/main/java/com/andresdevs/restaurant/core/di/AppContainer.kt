package com.andresdevs.restaurant.core.di

import android.content.Context
import androidx.room.Room
import com.andresdevs.restaurant.data.firebase.CategoriaFirebaseService
import com.andresdevs.restaurant.data.firebase.ProductoFirebaseService
import com.andresdevs.restaurant.data.firebase.UsuarioFirebaseService
import com.andresdevs.restaurant.data.local.db.RestaurantDatabase
import com.andresdevs.restaurant.data.repository.CategoriaRepositoryImpl
import com.andresdevs.restaurant.data.repository.ProductoRepositoryImpl
import com.andresdevs.restaurant.data.repository.UsuarioRepositoryImpl
import com.andresdevs.restaurant.domain.usecase.categoria.CreateCategoriaUseCase
import com.andresdevs.restaurant.domain.usecase.categoria.DeleteCategoriaUseCase
import com.andresdevs.restaurant.domain.usecase.categoria.GetCategoriaUseCase
import com.andresdevs.restaurant.domain.usecase.categoria.UpdateCategoriaUseCase
import com.andresdevs.restaurant.domain.usecase.producto.CreateProductoUseCase
import com.andresdevs.restaurant.domain.usecase.producto.DeleteProductoUseCase
import com.andresdevs.restaurant.domain.usecase.producto.GetProductoUseCase
import com.andresdevs.restaurant.domain.usecase.producto.UpdateProductoUseCase
import com.andresdevs.restaurant.domain.usecase.usuario.CreateUsuarioUseCase
import com.andresdevs.restaurant.domain.usecase.usuario.DeleteUsuarioUseCase
import com.andresdevs.restaurant.domain.usecase.usuario.GetUsuarioUseCase
import com.andresdevs.restaurant.domain.usecase.usuario.UpdateUsuarioUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AppContainer(context: Context) {
    val firestore: FirebaseFirestore = Firebase.firestore
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val database: RestaurantDatabase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            RestaurantDatabase::class.java,
            "restaurant.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    private val categoriaService by lazy { CategoriaFirebaseService(firestore) }
    private val productoService by lazy { ProductoFirebaseService(firestore) }
    private val usuarioService by lazy { UsuarioFirebaseService(firestore) }

    private val categoriaRepository by lazy { CategoriaRepositoryImpl(categoriaService) }
    private val productoRepository by lazy {
        ProductoRepositoryImpl(
            service = productoService,
            cacheDao = database.productCacheDao()
        )
    }
    private val usuarioRepository by lazy { UsuarioRepositoryImpl(usuarioService) }

    val getCategoriaUseCase by lazy { GetCategoriaUseCase(categoriaRepository) }
    val createCategoriaUseCase by lazy { CreateCategoriaUseCase(categoriaRepository) }
    val updateCategoriaUseCase by lazy { UpdateCategoriaUseCase(categoriaRepository) }
    val deleteCategoriaUseCase by lazy { DeleteCategoriaUseCase(categoriaRepository) }

    val getProductoUseCase by lazy { GetProductoUseCase(productoRepository) }
    val createProductoUseCase by lazy { CreateProductoUseCase(productoRepository) }
    val updateProductoUseCase by lazy { UpdateProductoUseCase(productoRepository) }
    val deleteProductoUseCase by lazy { DeleteProductoUseCase(productoRepository) }

    val getUsuarioUseCase by lazy { GetUsuarioUseCase(usuarioRepository) }
    val createUsuarioUseCase by lazy { CreateUsuarioUseCase(usuarioRepository) }
    val updateUsuarioUseCase by lazy { UpdateUsuarioUseCase(usuarioRepository) }
    val deleteUsuarioUseCase by lazy { DeleteUsuarioUseCase(usuarioRepository) }
}
