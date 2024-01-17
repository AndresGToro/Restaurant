package com.andresdevs.restaurant.datos

data class CategoriaItems(
    val name: String,
    val url: String,
)

fun getCategoriaItems() = listOf(
    CategoriaItems("Book", "1"),
    CategoriaItems("APPLE", "2"),
    CategoriaItems("PENCIL", "3"),
    CategoriaItems("LAPTOP", "1")
)
