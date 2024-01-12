package com.andresdevs.restaurant

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {

    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar temporizador
        scope.launch {
            delay(4000) // Esperar 4 segundos
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}
