package com.example.app_chaski.ui.pedido

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.app_chaski.R
import com.example.app_chaski.databinding.ActivityDetallePedidoBinding
import com.example.app_chaski.utils.CurrencyUtils
import com.example.app_chaski.utils.DateUtils
import com.example.app_chaski.utils.hide
import com.example.app_chaski.utils.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetallePedidoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetallePedidoBinding
    private val viewModel: DetallePedidoViewModel by viewModels()
    private var pedidoId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pedidoId = intent.getLongExtra("pedido_id", 0)

        setupToolbar()
        observeViewModel()

        viewModel.cargarDetallePedido(pedidoId)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.order_detail)
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.loading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.show()
                binding.scrollView.hide()
            } else {
                binding.progressBar.hide()
                binding.scrollView.show()
            }
        }

        viewModel.pedido.observe(this) { pedido ->
            binding.apply {
                tvNumeroPedido.text = getString(
                    R.string.order_number,
                    pedido.id.toString().padStart(6, '0')
                )
                tvRestaurante.text = pedido.nombreRestaurante
                tvDireccion.text = pedido.direccionCompleta
                tvFecha.text = DateUtils.formatDateTime(pedido.fechaCreacion)
                tvEstado.text = pedido.estado

                // Resumen de costos
                tvSubtotal.text = CurrencyUtils.formatPrice(pedido.subtotalProductos)
                tvCostoEnvio.text = CurrencyUtils.formatPrice(pedido.costoEnvio)
                tvImpuestos.text = CurrencyUtils.formatPrice(pedido.impuestos)
                tvTotal.text = CurrencyUtils.formatPrice(pedido.totalFinal)

                // Notas
                if (pedido.notasInstrucciones != null) {
                    tvNotas.text = pedido.notasInstrucciones
                    tvNotas.show()
                } else {
                    tvNotas.hide()
                }

                // Botón cancelar
                if (puedeCancelar(pedido.estado)) {
                    btnCancelar.show()
                    btnCancelar.setOnClickListener {
                        viewModel.cancelarPedido(pedido.id)
                    }
                } else {
                    btnCancelar.hide()
                }
            }
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }

    private fun puedeCancelar(estado: String): Boolean {
        return estado in listOf("PENDIENTE_PAGO", "CONFIRMADO_TIENDA", "EN_PREPARACION")
    }
}

