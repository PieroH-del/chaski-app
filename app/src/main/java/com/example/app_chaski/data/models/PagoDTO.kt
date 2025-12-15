package com.example.app_chaski.data.models

data class PagoDTO(
    val id: Long,
    val pedidoId: Long,
    val monto: Double,
    val metodo: String,
    val estado: String,
    val referenciaPasarela: String,
    val fechaPago: String?
)

data class PagoRequest(
    val pedidoId: Long,
    val monto: Double,
    val metodo: String // "EFECTIVO", "TARJETA_CREDITO", "TARJETA_DEBITO", "YAPE"
)

