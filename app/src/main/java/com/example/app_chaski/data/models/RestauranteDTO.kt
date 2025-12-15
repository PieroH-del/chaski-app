package com.example.app_chaski.data.models

data class RestauranteDTO(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val direccion: String,
    val telefono: String,
    val imagenUrl: String?,  // Campo legacy - mantener por compatibilidad
    val imagenLogoUrl: String?,
    val imagenPortadaUrl: String?,
    val horarioApertura: String,
    val horarioCierre: String,
    val calificacionPromedio: Double,
    val tiempoEntregaPromedio: Int,
    val costoEnvioBase: Double,
    val activo: Boolean,
    val estaAbierto: Boolean
)

