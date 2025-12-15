package com.example.app_chaski.ui.pedidos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.app_chaski.R
import com.example.app_chaski.data.models.PedidoDTO
import com.example.app_chaski.databinding.ItemPedidoBinding
import com.example.app_chaski.utils.CurrencyUtils
import com.example.app_chaski.utils.DateUtils
import com.example.app_chaski.utils.hide
import com.example.app_chaski.utils.show

class PedidosAdapter(
    private val onItemClick: (PedidoDTO) -> Unit,
    private val onCancelarClick: (PedidoDTO) -> Unit
) : ListAdapter<PedidoDTO, PedidosAdapter.ViewHolder>(PedidoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPedidoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onItemClick, onCancelarClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemPedidoBinding,
        private val onItemClick: (PedidoDTO) -> Unit,
        private val onCancelarClick: (PedidoDTO) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pedido: PedidoDTO) {
            binding.apply {
                tvRestaurante.text = pedido.nombreRestaurante
                tvNumeroPedido.text = root.context.getString(
                    R.string.order_number,
                    pedido.id.toString().padStart(6, '0')
                )
                tvTotal.text = CurrencyUtils.formatPrice(pedido.totalFinal)
                tvFecha.text = DateUtils.formatDateTime(pedido.fechaCreacion)

                // Estado del pedido
                val (estadoText, estadoColor) = getEstadoInfo(pedido.estado)
                tvEstado.text = estadoText
                tvEstado.setBackgroundColor(
                    ContextCompat.getColor(root.context, estadoColor)
                )

                // Botón cancelar solo visible para ciertos estados
                if (puedeCancelar(pedido.estado)) {
                    btnCancelar.show()
                    btnCancelar.setOnClickListener {
                        onCancelarClick(pedido)
                    }
                } else {
                    btnCancelar.hide()
                }

                root.setOnClickListener {
                    onItemClick(pedido)
                }
            }
        }

        private fun getEstadoInfo(estado: String): Pair<String, Int> {
            return when (estado) {
                "PENDIENTE_PAGO" -> Pair(
                    binding.root.context.getString(R.string.estado_pendiente_pago),
                    R.color.estado_pendiente
                )
                "CONFIRMADO_TIENDA" -> Pair(
                    binding.root.context.getString(R.string.estado_confirmado),
                    R.color.estado_confirmado
                )
                "EN_PREPARACION" -> Pair(
                    binding.root.context.getString(R.string.estado_preparacion),
                    R.color.estado_preparacion
                )
                "LISTO_PARA_RECOGER" -> Pair(
                    binding.root.context.getString(R.string.estado_listo),
                    R.color.estado_listo
                )
                "EN_CAMINO" -> Pair(
                    binding.root.context.getString(R.string.estado_en_camino),
                    R.color.estado_camino
                )
                "ENTREGADO" -> Pair(
                    binding.root.context.getString(R.string.estado_entregado),
                    R.color.estado_entregado
                )
                "CANCELADO" -> Pair(
                    binding.root.context.getString(R.string.estado_cancelado),
                    R.color.estado_cancelado
                )
                else -> Pair(estado, R.color.gray)
            }
        }

        private fun puedeCancelar(estado: String): Boolean {
            return estado in listOf("PENDIENTE_PAGO", "CONFIRMADO_TIENDA", "EN_PREPARACION")
        }
    }

    private class PedidoDiffCallback : DiffUtil.ItemCallback<PedidoDTO>() {
        override fun areItemsTheSame(oldItem: PedidoDTO, newItem: PedidoDTO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PedidoDTO, newItem: PedidoDTO): Boolean {
            return oldItem == newItem
        }
    }
}

