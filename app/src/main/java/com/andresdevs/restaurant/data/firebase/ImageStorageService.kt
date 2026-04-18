package com.andresdevs.restaurant.data.firebase

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ImageStorageService @Inject constructor(
    private val storage: FirebaseStorage
) {
    suspend fun uploadProductImage(productId: String, bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream)
        val data = stream.toByteArray()
        val ref = storage.reference.child("products/$productId.jpg")
        ref.putBytes(data).await()
        return ref.downloadUrl.await().toString()
    }
}
