package com.example.app_chaski.ui.productos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.app_chaski.data.models.ItemCarrito
import com.example.app_chaski.data.models.OpcionDTO
import com.example.app_chaski.data.models.ProductoDTO
import com.example.app_chaski.databinding.BottomSheetProductoOpcionesBinding
import com.example.app_chaski.utils.CarritoManager
import com.example.app_chaski.utils.loadImage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProductoOpcionesBottomSheet(
    private val producto: ProductoDTO,
    private val restauranteId: Long,
    private val onProductoAgregado: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetProductoOpcionesBinding? = null
    private val binding get() = _binding!!

    private var cantidad = 1
    private val opcionesSeleccionadas = mutableListOf<OpcionDTO>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetProductoOpcionesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        // Cargar imagen del producto
        binding.ivProducto.loadImage(producto.imagenUrl)

        binding.tvNombreProducto.text = producto.nombre
        binding.tvDescripcion.text = producto.descripcion
        binding.tvPrecio.text = String.format("S/. %.2f", producto.precio)

        binding.btnDecrementar.setOnClickListener {
            if (cantidad > 1) {
                cantidad--
                actualizarCantidad()
            }
        }

        binding.btnIncrementar.setOnClickListener {
            if (cantidad < 10) {
                cantidad++
                actualizarCantidad()
            }
        }

        binding.btnAgregar.setOnClickListener {
            agregarAlCarrito()
        }

        actualizarCantidad()
    }

    private fun actualizarCantidad() {
        binding.tvCantidad.text = cantidad.toString()
        actualizarPrecioTotal()
    }

    private fun actualizarPrecioTotal() {
        val precioBase = producto.precio
        val precioOpciones = opcionesSeleccionadas.sumOf { it.precioExtra }
        val total = (precioBase + precioOpciones) * cantidad
        binding.btnAgregar.text = String.format("Agregar al Carrito - S/. %.2f", total)
    }

    private fun agregarAlCarrito() {
        val precioBase = producto.precio
        val precioOpciones = opcionesSeleccionadas.sumOf { it.precioExtra }
        val precioTotal = (precioBase + precioOpciones) * cantidad

        val item = ItemCarrito(
            producto = producto,
            cantidad = cantidad,
            opcionesSeleccionadas = opcionesSeleccionadas.toList(),
            precioTotal = precioTotal
        )

        // Guardar el restaurante ID si el carrito está vacío o si es el mismo restaurante
        if (CarritoManager.restauranteId == null) {
            CarritoManager.restauranteId = restauranteId
        } else if (CarritoManager.restauranteId != restauranteId) {
            // Si el usuario intenta agregar de otro restaurante, limpiar carrito
            CarritoManager.limpiarCarrito()
            CarritoManager.restauranteId = restauranteId
        }

        CarritoManager.agregarItem(item)
        Toast.makeText(requireContext(), "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
        onProductoAgregado()
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ProductoOpcionesBottomSheet"

        fun newInstance(
            producto: ProductoDTO,
            restauranteId: Long,
            onProductoAgregado: () -> Unit
        ): ProductoOpcionesBottomSheet {
            return ProductoOpcionesBottomSheet(producto, restauranteId, onProductoAgregado)
        }
    }
}

