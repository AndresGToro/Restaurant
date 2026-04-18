package com.andresdevs.restaurant.core.notifications

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class RestaurantMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Token actualizado: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val title = message.notification?.title ?: "Notificacion"
        val body = message.notification?.body ?: "Sin contenido"
        Log.d("FCM", "Push recibida: $title - $body")
        FirebaseCrashlytics.getInstance().log("Push recibida: $title")
    }
}
