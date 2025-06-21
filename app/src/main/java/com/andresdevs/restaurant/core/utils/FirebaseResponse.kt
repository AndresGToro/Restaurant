package com.andresdevs.restaurant.core.utils

sealed class FirebaseResponse<out T> {
    object Loading : FirebaseResponse<Nothing>()
    data class Success<T>(val data: T) : FirebaseResponse<T>()
    data class Error(val message: String) : FirebaseResponse<Nothing>()
}
