package com.example.app_chaski.data.models

data class ProductoDTO(
    val id: Long,
    val restauranteId: Long,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagenUrl: String?,
    val categoria: String,
    val disponible: Boolean,
    val gruposOpciones: List<GrupoOpcionesDTO>
)

data class GrupoOpcionesDTO(
    val id: Long,
    val nombre: String,
    val esObligatorio: Boolean,
    val seleccionMinima: Int,
    val seleccionMaxima: Int,
    val opciones: List<OpcionDTO>
)

data class OpcionDTO(
    val id: Long,
    val nombre: String,
    val precioExtra: Double
)

