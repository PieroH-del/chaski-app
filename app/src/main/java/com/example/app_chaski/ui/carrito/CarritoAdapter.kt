package com.example.app_chaski.ui.carrito

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.app_chaski.data.models.ItemCarrito
import com.example.app_chaski.databinding.ItemCarritoBinding
import com.example.app_chaski.utils.CurrencyUtils
import com.example.app_chaski.utils.loadImage

class CarritoAdapter(
    private val onCantidadChanged: (position: Int, nuevaCantidad: Int) -> Unit,
    private val onEliminarClick: (position: Int) -> Unit
) : ListAdapter<ItemCarrito, CarritoAdapter.ViewHolder>(ItemCarritoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCarritoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onCantidadChanged, onEliminarClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    class ViewHolder(
        private val binding: ItemCarritoBinding,
        private val onCantidadChanged: (position: Int, nuevaCantidad: Int) -> Unit,
        private val onEliminarClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemCarrito, position: Int) {
            binding.apply {
                tvNombreProducto.text = item.producto.nombre
                tvPrecioUnitario.text = CurrencyUtils.formatPrice(item.precioTotal)
                tvCantidad.text = item.cantidad.toString()

                ivProducto.loadImage(item.producto.imagenUrl)

                // Mostrar opciones seleccionadas
                if (item.opcionesSeleccionadas.isNotEmpty()) {
                    val opcionesText = item.opcionesSeleccionadas.joinToString(", ") { opcion ->
                        if (opcion.precioExtra > 0) {
                            "${opcion.nombre} +${CurrencyUtils.formatPrice(opcion.precioExtra)}"
                        } else {
                            opcion.nombre
                        }
                    }
                    tvOpciones.text = opcionesText
                } else {
                    tvOpciones.text = ""
                }

                btnDecrementar.setOnClickListener {
                    if (item.cantidad > 1) {
                        onCantidadChanged(position, item.cantidad - 1)
                    }
                }

                btnIncrementar.setOnClickListener {
                    if (item.cantidad < 10) {
                        onCantidadChanged(position, item.cantidad + 1)
                    }
                }

                btnEliminar.setOnClickListener {
                    onEliminarClick(position)
                }
            }
        }
    }

    private class ItemCarritoDiffCallback : DiffUtil.ItemCallback<ItemCarrito>() {
        override fun areItemsTheSame(oldItem: ItemCarrito, newItem: ItemCarrito): Boolean {
            return oldItem.producto.id == newItem.producto.id &&
                   oldItem.opcionesSeleccionadas == newItem.opcionesSeleccionadas
        }

        override fun areContentsTheSame(oldItem: ItemCarrito, newItem: ItemCarrito): Boolean {
            return oldItem == newItem
        }
    }
}

