package com.andresdevs.restaurant.data.firebase

import com.google.firebase.functions.FirebaseFunctions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FunctionsService @Inject constructor(
    private val functions: FirebaseFunctions
) {
    suspend fun requestDailySalesReport(date: String): Result<String> {
        return try {
            val payload = hashMapOf("date" to date)
            val result = functions
                .getHttpsCallable("generateDailySalesReport")
                .call(payload)
                .await()
            Result.success(result.getData()?.toString().orEmpty())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
