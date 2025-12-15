package com.example.app_chaski.data.models

data class UsuarioDTO(
    val id: Long,
    val nombre: String,
    val email: String,
    val telefono: String,
    val imagenPerfilUrl: String?,
    val fechaRegistro: String,
    val activo: Boolean
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class UsuarioRegistroRequest(
    val nombre: String,
    val email: String,
    val password: String,
    val telefono: String,
    val imagenPerfilUrl: String? = null
)

data class UsuarioActualizacionRequest(
    val nombre: String?,
    val telefono: String?,
    val imagenPerfilUrl: String?
)

