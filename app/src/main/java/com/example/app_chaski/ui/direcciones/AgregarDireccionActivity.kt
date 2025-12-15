package com.example.app_chaski.ui.direcciones

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.app_chaski.R
import com.example.app_chaski.data.models.DireccionDTO
import com.example.app_chaski.data.models.DireccionRequest
import com.example.app_chaski.databinding.ActivityAgregarDireccionBinding
import com.example.app_chaski.utils.SessionManager
import com.example.app_chaski.utils.hide
import com.example.app_chaski.utils.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgregarDireccionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarDireccionBinding
    private val viewModel: AgregarDireccionViewModel by viewModels()
    private lateinit var sessionManager: SessionManager

    private var modoEdicion = false
    private var direccionActual: DireccionDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarDireccionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        // Obtener datos si es modo edición
        modoEdicion = intent.getBooleanExtra("modo_edicion", false)
        direccionActual = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("direccion", DireccionDTO::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("direccion")
        }

        setupToolbar()
        cargarDatosEdicion()
        setupViews()
        observeViewModel()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = if (modoEdicion) {
                getString(R.string.edit_address)
            } else {
                getString(R.string.add_address)
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun cargarDatosEdicion() {
        direccionActual?.let { direccion ->
            binding.apply {
                etAlias.setText(direccion.alias)
                etDireccion.setText(direccion.direccionCompleta)
                etReferencia.setText(direccion.referencia ?: "")
                switchPredeterminada.isChecked = direccion.predeterminada
            }
        }
    }

    private fun setupViews() {
        binding.btnGuardar.setOnClickListener {
            val alias = binding.etAlias.text.toString().trim()
            val direccion = binding.etDireccion.text.toString().trim()
            val referencia = binding.etReferencia.text.toString().trim()
            val predeterminada = binding.switchPredeterminada.isChecked

            if (validateFields(alias, direccion)) {
                val request = DireccionRequest(
                    usuarioId = sessionManager.getUsuarioId(),
                    alias = alias,
                    direccionCompleta = direccion,
                    referencia = referencia.ifBlank { null },
                    latitud = direccionActual?.latitud ?: -12.0464,
                    longitud = direccionActual?.longitud ?: -77.0428,
                    predeterminada = predeterminada
                )

                if (modoEdicion && direccionActual != null) {
                    viewModel.actualizarDireccion(direccionActual!!.id, request)
                } else {
                    viewModel.guardarDireccion(request)
                }
            }
        }
    }

    private fun validateFields(alias: String, direccion: String): Boolean {
        var isValid = true

        if (alias.isBlank()) {
            binding.tilAlias.error = getString(R.string.error_field_required)
            isValid = false
        } else {
            binding.tilAlias.error = null
        }

        if (direccion.isBlank()) {
            binding.tilDireccion.error = getString(R.string.error_field_required)
            isValid = false
        } else {
            binding.tilDireccion.error = null
        }

        return isValid
    }

    private fun observeViewModel() {
        viewModel.loading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.show()
                binding.btnGuardar.isEnabled = false
            } else {
                binding.progressBar.hide()
                binding.btnGuardar.isEnabled = true
            }
        }

        viewModel.direccionGuardada.observe(this) {
            Snackbar.make(binding.root, "Dirección guardada", Snackbar.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }
}

