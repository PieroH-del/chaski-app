package com.example.app_chaski.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.app_chaski.ui.auth.LoginActivity
import com.example.app_chaski.ui.main.MainActivity
import com.example.app_chaski.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionManager = SessionManager(this)

        // Esperar 2 segundos antes de navegar
        Handler(Looper.getMainLooper()).postDelayed({
            checkSession()
        }, 2000)
    }

    private fun checkSession() {
        val intent = if (sessionManager.isLoggedIn()) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }

        startActivity(intent)
        finish()
    }
}

