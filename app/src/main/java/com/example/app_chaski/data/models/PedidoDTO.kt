package com.example.app_chaski.data.models

data class PedidoDTO(
    val id: Long,
    val usuarioId: Long,
    val restauranteId: Long,
    val nombreRestaurante: String?,
    val direccionEntregaId: Long?,
    val direccionCompleta: String?,
    val subtotalProductos: Double,
    val costoEnvio: Double,
    val impuestos: Double,
    val totalFinal: Double,
    val estado: String,
    val notasInstrucciones: String?,
    val fechaCreacion: String,
    val fechaActualizacion: String?,
    val detalles: List<DetallePedidoDTO>?
)

data class DetallePedidoDTO(
    val id: Long,
    val productoId: Long,
    val nombreProducto: String,
    val cantidad: Int,
    val precioUnitario: Double,
    val subtotalItem: Double,
    val opciones: List<OpcionDetallePedidoDTO>
)

data class OpcionDetallePedidoDTO(
    val id: Long,
    val opcionId: Long,
    val nombreOpcion: String,
    val precioExtraCobrado: Double
)

data class PedidoRequest(
    val usuarioId: Long,
    val restauranteId: Long,
    val direccionEntregaId: Long,
    val notasInstrucciones: String?,
    val detalles: List<DetallePedidoRequest>
)

data class DetallePedidoRequest(
    val productoId: Long,
    val cantidad: Int,
    val opciones: List<OpcionRequest>
)

data class OpcionRequest(
    val opcionId: Long
)

