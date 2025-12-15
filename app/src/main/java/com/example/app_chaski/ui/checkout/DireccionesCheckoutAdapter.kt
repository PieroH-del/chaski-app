package com.example.app_chaski.ui.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.app_chaski.R
import com.example.app_chaski.data.models.DireccionDTO
import com.example.app_chaski.databinding.ItemDireccionCheckoutBinding

class DireccionesCheckoutAdapter(
    private val onDireccionClick: (DireccionDTO) -> Unit
) : ListAdapter<DireccionDTO, DireccionesCheckoutAdapter.DireccionViewHolder>(DireccionDiffCallback()) {

    private var direccionSeleccionadaId: Long = -1

    fun setDireccionSeleccionada(id: Long) {
        val previousId = direccionSeleccionadaId
        direccionSeleccionadaId = id

        // Notificar cambios solo en los items afectados
        currentList.forEachIndexed { index, direccion ->
            if (direccion.id == previousId || direccion.id == id) {
                notifyItemChanged(index)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DireccionViewHolder {
        val binding = ItemDireccionCheckoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DireccionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DireccionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DireccionViewHolder(
        private val binding: ItemDireccionCheckoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(direccion: DireccionDTO) {
            binding.apply {
                // Mostrar alias si existe, sino "Mi dirección"
                tvAlias.text = direccion.alias ?: "Mi dirección"
                tvDireccion.text = direccion.direccionCompleta

                // Mostrar referencia si existe
                tvReferencia.text = if (!direccion.referencia.isNullOrEmpty()) {
                    "Ref: ${direccion.referencia}"
                } else {
                    ""
                }

                // Marcar si está seleccionada
                val isSelected = direccion.id == direccionSeleccionadaId
                rbSeleccionar.isChecked = isSelected

                // Resaltar la tarjeta seleccionada
                root.strokeWidth = if (isSelected) 4 else 1
                root.strokeColor = if (isSelected) {
                    root.context.getColor(R.color.primary)
                } else {
                    root.context.getColor(R.color.outline)
                }

                // Click en toda la tarjeta
                root.setOnClickListener {
                    onDireccionClick(direccion)
                }

                // Click en el radio button
                rbSeleccionar.setOnClickListener {
                    onDireccionClick(direccion)
                }
            }
        }
    }

    private class DireccionDiffCallback : DiffUtil.ItemCallback<DireccionDTO>() {
        override fun areItemsTheSame(oldItem: DireccionDTO, newItem: DireccionDTO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DireccionDTO, newItem: DireccionDTO): Boolean {
            return oldItem == newItem
        }
    }
}

