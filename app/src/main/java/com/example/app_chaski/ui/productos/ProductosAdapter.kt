package com.example.app_chaski.ui.productos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.app_chaski.data.models.ProductoDTO
import com.example.app_chaski.databinding.ItemProductoBinding
import com.example.app_chaski.utils.CurrencyUtils
import com.example.app_chaski.utils.hide
import com.example.app_chaski.utils.loadImage
import com.example.app_chaski.utils.show

class ProductosAdapter(
    private val onItemClick: (ProductoDTO) -> Unit
) : ListAdapter<ProductoDTO, ProductosAdapter.ViewHolder>(ProductoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemProductoBinding,
        private val onItemClick: (ProductoDTO) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(producto: ProductoDTO) {
            binding.apply {
                tvNombre.text = producto.nombre
                tvDescripcion.text = producto.descripcion
                tvPrecio.text = CurrencyUtils.formatPrice(producto.precio)

                ivProducto.loadImage(producto.imagenUrl)

                if (producto.disponible) {
                    tvNoDisponible.hide()
                    root.alpha = 1.0f
                    root.isEnabled = true
                } else {
                    tvNoDisponible.show()
                    root.alpha = 0.5f
                    root.isEnabled = false
                }

                root.setOnClickListener {
                    if (producto.disponible) {
                        onItemClick(producto)
                    }
                }
            }
        }
    }

    private class ProductoDiffCallback : DiffUtil.ItemCallback<ProductoDTO>() {
        override fun areItemsTheSame(oldItem: ProductoDTO, newItem: ProductoDTO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductoDTO, newItem: ProductoDTO): Boolean {
            return oldItem == newItem
        }
    }
}

