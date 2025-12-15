package com.example.app_chaski.ui.pedido

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app_chaski.R
import com.example.app_chaski.databinding.ActivityPedidoExitoBinding
import com.example.app_chaski.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PedidoExitoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPedidoExitoBinding
    private var pedidoId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPedidoExitoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pedidoId = intent.getLongExtra("pedido_id", 0)

        setupViews()
    }

    private fun setupViews() {
        val numeroPedido = "#${pedidoId.toString().padStart(6, '0')}"
        binding.tvNumeroPedido.text = getString(R.string.order_number, numeroPedido)

        binding.btnVerPedido.setOnClickListener {
            val intent = Intent(this, DetallePedidoActivity::class.java).apply {
                putExtra("pedido_id", pedidoId)
            }
            startActivity(intent)
            finish()
        }

        binding.btnVolverInicio.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}

