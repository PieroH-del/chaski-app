package com.example.app_chaski.utils

import com.example.app_chaski.data.models.ItemCarrito
import com.example.app_chaski.data.models.OpcionDTO
import com.example.app_chaski.data.models.ProductoDTO

object CarritoManager {
    private val _items = mutableListOf<ItemCarrito>()
    val items: List<ItemCarrito> get() = _items.toList()
    var restauranteId: Long? = null

    fun obtenerItems(): List<ItemCarrito> = _items.toList()

    fun agregarItem(item: ItemCarrito) {
        _items.add(item)
    }

    fun eliminarItem(index: Int) {
        if (index in _items.indices) {
            _items.removeAt(index)
        }
    }

    fun actualizarCantidad(index: Int, nuevaCantidad: Int) {
        if (index in _items.indices && nuevaCantidad > 0) {
            val item = _items[index]
            val nuevoPrecioTotal = calcularPrecioItem(
                item.producto,
                item.opcionesSeleccionadas,
                nuevaCantidad
            )
            _items[index] = item.copy(
                cantidad = nuevaCantidad,
                precioTotal = nuevoPrecioTotal
            )
        }
    }

    fun calcularSubtotal(): Double {
        return _items.sumOf { it.precioTotal }
    }

    fun calcularCostoEnvio(): Double {
        return if (_items.isNotEmpty()) 5.0 else 0.0
    }

    fun calcularImpuestos(): Double {
        val subtotal = calcularSubtotal()
        return subtotal * 0.18 // 18% de impuestos
    }

    fun calcularTotal(): Double {
        return calcularSubtotal() + calcularCostoEnvio() + calcularImpuestos()
    }

    fun limpiarCarrito() {
        _items.clear()
        restauranteId = null
    }

    fun cantidadItems(): Int {
        return _items.sumOf { it.cantidad }
    }

    fun estaVacio(): Boolean {
        return _items.isEmpty()
    }

    private fun calcularPrecioItem(
        producto: ProductoDTO,
        opciones: List<OpcionDTO>,
        cantidad: Int
    ): Double {
        val precioBase = producto.precio
        val precioOpciones = opciones.sumOf { it.precioExtra }
        return (precioBase + precioOpciones) * cantidad
    }
}

