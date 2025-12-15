package com.example.app_chaski.ui.direcciones

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_chaski.R
import com.example.app_chaski.databinding.ActivityDireccionesBinding
import com.example.app_chaski.utils.SessionManager
import com.example.app_chaski.utils.hide
import com.example.app_chaski.utils.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DireccionesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDireccionesBinding
    private val viewModel: DireccionesViewModel by viewModels()
    private lateinit var adapter: DireccionesAdapter
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDireccionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setupToolbar()
        setupRecyclerView()
        setupFab()
        observeViewModel()

        val usuarioId = sessionManager.getUsuarioId()
        viewModel.cargarDirecciones(usuarioId)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.addresses)
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        adapter = DireccionesAdapter(
            onEditClick = { direccion ->
                val intent = Intent(this, AgregarDireccionActivity::class.java).apply {
                    putExtra("modo_edicion", true)
                    putExtra("direccion", direccion)
                }
                startActivity(intent)
            },
            onDeleteClick = { direccion ->
                mostrarDialogEliminar(direccion.id)
            }
        )

        binding.rvDirecciones.layoutManager = LinearLayoutManager(this)
        binding.rvDirecciones.adapter = adapter
    }

    private fun setupFab() {
        binding.fabAgregar.setOnClickListener {
            val intent = Intent(this, AgregarDireccionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun mostrarDialogEliminar(direccionId: Long) {
        AlertDialog.Builder(this)
            .setTitle(R.string.delete)
            .setMessage(R.string.delete_address_confirm)
            .setPositiveButton(R.string.yes) { _, _ ->
                val usuarioId = sessionManager.getUsuarioId()
                viewModel.eliminarDireccion(direccionId, usuarioId)
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    private fun observeViewModel() {
        viewModel.loading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
            }
        }

        viewModel.direcciones.observe(this) { direcciones ->
            if (direcciones.isEmpty()) {
                binding.rvDirecciones.hide()
                binding.tvEmptyState.show()
            } else {
                binding.rvDirecciones.show()
                binding.tvEmptyState.hide()
                adapter.submitList(direcciones)
            }
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val usuarioId = sessionManager.getUsuarioId()
        viewModel.cargarDirecciones(usuarioId)
    }
}

