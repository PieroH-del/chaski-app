package com.example.app_chaski.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.app_chaski.R
import com.example.app_chaski.data.models.RestauranteDTO
import com.example.app_chaski.databinding.ItemRestauranteBinding
import com.example.app_chaski.utils.CurrencyUtils
import com.example.app_chaski.utils.loadImage

class RestaurantesAdapter(
    private val onItemClick: (RestauranteDTO) -> Unit
) : ListAdapter<RestauranteDTO, RestaurantesAdapter.ViewHolder>(RestauranteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRestauranteBinding.inflate(
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
        private val binding: ItemRestauranteBinding,
        private val onItemClick: (RestauranteDTO) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurante: RestauranteDTO) {
            binding.apply {
                tvNombre.text = restaurante.nombre
                tvDescripcion.text = restaurante.descripcion
                tvCalificacion.text = String.format("%.1f", restaurante.calificacionPromedio)
                tvTiempoEntrega.text = root.context.getString(
                    R.string.delivery_time,
                    restaurante.tiempoEntregaPromedio
                )
                tvCostoEnvio.text = CurrencyUtils.formatPrice(restaurante.costoEnvioBase) + " envío"

                // Usar imagenLogoUrl si está disponible, sino usar imagenUrl como fallback
                val imagenAMostrar = restaurante.imagenLogoUrl ?: restaurante.imagenUrl
                ivRestaurante.loadImage(imagenAMostrar)

                // Estado abierto/cerrado
                if (restaurante.estaAbierto) {
                    tvEstado.text = root.context.getString(R.string.open)
                    tvEstado.setBackgroundColor(
                        ContextCompat.getColor(root.context, R.color.tienda_abierta)
                    )
                } else {
                    tvEstado.text = root.context.getString(R.string.closed)
                    tvEstado.setBackgroundColor(
                        ContextCompat.getColor(root.context, R.color.tienda_cerrada)
                    )
                }

                root.setOnClickListener {
                    onItemClick(restaurante)
                }
            }
        }
    }

    private class RestauranteDiffCallback : DiffUtil.ItemCallback<RestauranteDTO>() {
        override fun areItemsTheSame(oldItem: RestauranteDTO, newItem: RestauranteDTO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RestauranteDTO, newItem: RestauranteDTO): Boolean {
            return oldItem == newItem
        }
    }
}

