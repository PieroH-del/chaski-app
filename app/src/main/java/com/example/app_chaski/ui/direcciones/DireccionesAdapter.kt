package com.example.app_chaski.ui.direcciones

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.app_chaski.data.models.DireccionDTO
import com.example.app_chaski.databinding.ItemDireccionBinding
import com.example.app_chaski.utils.hide
import com.example.app_chaski.utils.show

class DireccionesAdapter(
    private val onEditClick: (DireccionDTO) -> Unit,
    private val onDeleteClick: (DireccionDTO) -> Unit
) : ListAdapter<DireccionDTO, DireccionesAdapter.ViewHolder>(DireccionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDireccionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onEditClick, onDeleteClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemDireccionBinding,
        private val onEditClick: (DireccionDTO) -> Unit,
        private val onDeleteClick: (DireccionDTO) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(direccion: DireccionDTO) {
            binding.apply {
                tvAlias.text = direccion.alias
                tvDireccion.text = direccion.direccionCompleta
                tvReferencia.text = direccion.referencia ?: ""

                if (direccion.predeterminada) {
                    tvPredeterminada.show()
                } else {
                    tvPredeterminada.hide()
                }

                btnEditar.setOnClickListener {
                    onEditClick(direccion)
                }

                btnEliminar.setOnClickListener {
                    onDeleteClick(direccion)
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

