package com.example.app_chaski.ui.carrito

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_chaski.R
import com.example.app_chaski.databinding.ActivityCarritoBinding
import com.example.app_chaski.ui.checkout.CheckoutActivity
import com.example.app_chaski.utils.CarritoManager
import com.example.app_chaski.utils.CurrencyUtils
import com.example.app_chaski.utils.hide
import com.example.app_chaski.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarritoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarritoBinding
    private lateinit var adapter: CarritoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarritoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupResumen()
        actualizarVista()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.my_cart)
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        adapter = CarritoAdapter(
            onCantidadChanged = { position, nuevaCantidad ->
                CarritoManager.actualizarCantidad(position, nuevaCantidad)
                actualizarVista()
            },
            onEliminarClick = { position ->
                mostrarDialogEliminar(position)
            }
        )

        binding.rvCarrito.layoutManager = LinearLayoutManager(this)
        binding.rvCarrito.adapter = adapter
    }

    private fun setupResumen() {
        binding.btnCheckout.setOnClickListener {
            if (!CarritoManager.estaVacio()) {
                val intent = Intent(this, CheckoutActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun actualizarVista() {
        val items = CarritoManager.obtenerItems()

        if (items.isEmpty()) {
            binding.rvCarrito.hide()
            binding.cardResumen.hide()
            binding.tvEmptyState.show()
            binding.btnCheckout.isEnabled = false
        } else {
            binding.rvCarrito.show()
            binding.cardResumen.show()
            binding.tvEmptyState.hide()
            binding.btnCheckout.isEnabled = true

            adapter.submitList(items)
            actualizarResumen()
        }
    }

    private fun actualizarResumen() {
        val subtotal = CarritoManager.calcularSubtotal()
        val costoEnvio = 5.0 // Obtener del restaurante
        val impuestos = subtotal * 0.18
        val total = subtotal + costoEnvio + impuestos

        binding.apply {
            tvSubtotal.text = CurrencyUtils.formatPrice(subtotal)
            tvCostoEnvio.text = CurrencyUtils.formatPrice(costoEnvio)
            tvImpuestos.text = CurrencyUtils.formatPrice(impuestos)
            tvTotal.text = CurrencyUtils.formatPrice(total)
        }
    }

    private fun mostrarDialogEliminar(position: Int) {
        AlertDialog.Builder(this)
            .setTitle(R.string.delete_item_title)
            .setMessage(R.string.delete_item_message)
            .setPositiveButton(R.string.delete) { _, _ ->
                CarritoManager.eliminarItem(position)
                actualizarVista()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        actualizarVista()
    }
}

