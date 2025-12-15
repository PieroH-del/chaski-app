package com.example.app_chaski.ui.productos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_chaski.R
import com.example.app_chaski.databinding.ActivityProductosBinding
import com.example.app_chaski.ui.carrito.CarritoActivity
import com.example.app_chaski.utils.CarritoManager
import com.example.app_chaski.utils.hide
import com.example.app_chaski.utils.loadImage
import com.example.app_chaski.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductosBinding
    private val viewModel: ProductosViewModel by viewModels()
    private lateinit var adapter: ProductosAdapter

    private var restauranteId: Long = 0
    private var restauranteNombre: String = ""
    private var costoEnvio: Double = 0.0
    private var imagenPortadaUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        obtenerExtras()
        setupToolbar()
        setupRecyclerView()
        setupFab()
        observeViewModel()

        viewModel.cargarProductos(restauranteId)
    }

    private fun obtenerExtras() {
        restauranteId = intent.getLongExtra("restaurante_id", 0)
        restauranteNombre = intent.getStringExtra("restaurante_nombre") ?: ""
        costoEnvio = intent.getDoubleExtra("costo_envio", 0.0)
        imagenPortadaUrl = intent.getStringExtra("imagen_portada")
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = restauranteNombre
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        // Configurar CollapsingToolbar
        binding.collapsingToolbar.title = restauranteNombre

        // Cargar imagen de portada
        // Si no hay imagen de portada, mostrar placeholder
        if (!imagenPortadaUrl.isNullOrEmpty()) {
            binding.ivPortadaRestaurante.loadImage(imagenPortadaUrl)
        } else {
            // Usar imagen genérica si no hay portada
            binding.ivPortadaRestaurante.setImageResource(android.R.drawable.ic_menu_gallery)
        }
    }

    private fun setupRecyclerView() {
        adapter = ProductosAdapter { producto ->
            ProductoOpcionesBottomSheet.newInstance(producto, restauranteId) {
                actualizarBadgeCarrito()
            }.show(supportFragmentManager, "ProductoOpciones")
        }

        binding.rvProductos.layoutManager = LinearLayoutManager(this)
        binding.rvProductos.adapter = adapter
    }

    private fun setupFab() {
        actualizarBadgeCarrito()

        binding.fabCarrito.setOnClickListener {
            if (!CarritoManager.estaVacio()) {
                val intent = Intent(this, CarritoActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.cart_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.loading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
            }
        }

        viewModel.productos.observe(this) { productos ->
            if (productos.isEmpty()) {
                binding.rvProductos.hide()
                binding.tvEmptyState.show()
            } else {
                binding.rvProductos.show()
                binding.tvEmptyState.hide()
                adapter.submitList(productos)
            }
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }

    fun actualizarBadgeCarrito() {
        val cantidadItems = CarritoManager.cantidadItems()
        if (cantidadItems > 0) {
            binding.fabCarrito.show()
            // Aquí podrías agregar un badge al FAB
        } else {
            // Aún mostrar el FAB pero sin badge
        }
    }

    override fun onResume() {
        super.onResume()
        actualizarBadgeCarrito()
    }
}

