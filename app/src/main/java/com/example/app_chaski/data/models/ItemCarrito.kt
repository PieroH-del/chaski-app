package com.example.app_chaski.data.models

// Modelo para gestión local del carrito
data class ItemCarrito(
    val producto: ProductoDTO,
    val cantidad: Int,
    val opcionesSeleccionadas: List<OpcionDTO>,
    val precioTotal: Double
)
