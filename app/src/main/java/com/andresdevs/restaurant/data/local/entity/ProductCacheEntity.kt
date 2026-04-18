package com.andresdevs.restaurant.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products_cache")
data class ProductCacheEntity(
    @PrimaryKey
    val codeProducto: String,
    val codeCategoriaProducto: String,
    val nameProducto: String,
    val precioProducto: String,
    val urlProducto: String,
    val estadoProducto: String
)
