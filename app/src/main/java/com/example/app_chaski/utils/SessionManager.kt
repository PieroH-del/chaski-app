package com.example.app_chaski.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.app_chaski.data.models.UsuarioDTO

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "chaski_session"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USUARIO_ID = "usuario_id"
        private const val KEY_USUARIO_NOMBRE = "usuario_nombre"
        private const val KEY_USUARIO_EMAIL = "usuario_email"
        private const val KEY_USUARIO_TELEFONO = "usuario_telefono"
        private const val KEY_USUARIO_IMAGEN = "usuario_imagen"
    }

    fun guardarSesion(usuario: UsuarioDTO) {
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putLong(KEY_USUARIO_ID, usuario.id)
            putString(KEY_USUARIO_NOMBRE, usuario.nombre)
            putString(KEY_USUARIO_EMAIL, usuario.email)
            putString(KEY_USUARIO_TELEFONO, usuario.telefono)
            putString(KEY_USUARIO_IMAGEN, usuario.imagenPerfilUrl)
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUsuarioId(): Long {
        return prefs.getLong(KEY_USUARIO_ID, 0L)
    }

    fun getUsuarioNombre(): String? {
        return prefs.getString(KEY_USUARIO_NOMBRE, null)
    }

    fun getUsuarioEmail(): String? {
        return prefs.getString(KEY_USUARIO_EMAIL, null)
    }

    fun getUsuarioTelefono(): String? {
        return prefs.getString(KEY_USUARIO_TELEFONO, null)
    }

    fun getUsuarioImagen(): String? {
        return prefs.getString(KEY_USUARIO_IMAGEN, null)
    }

    fun actualizarUsuario(usuario: UsuarioDTO) {
        guardarSesion(usuario)
    }

    fun cerrarSesion() {
        prefs.edit().clear().apply()
        CarritoManager.limpiarCarrito()
    }
}

