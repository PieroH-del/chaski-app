package com.example.app_chaski

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ChaskiApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Inicializar Timber para logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

