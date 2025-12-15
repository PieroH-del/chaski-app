package com.example.app_chaski.ui.perfil

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.app_chaski.R
import com.example.app_chaski.data.models.UsuarioActualizacionRequest
import com.example.app_chaski.databinding.ActivityEditarPerfilBinding
import com.example.app_chaski.utils.SessionManager
import com.example.app_chaski.utils.ValidationUtils
import com.example.app_chaski.utils.hide
import com.example.app_chaski.utils.loadCircularImage
import com.example.app_chaski.utils.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditarPerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarPerfilBinding
    private val viewModel: PerfilViewModel by viewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setupToolbar()
        loadUserData()
        setupViews()
        observeViewModel()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.edit_profile)
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun loadUserData() {
        binding.apply {
            etNombre.setText(sessionManager.getUsuarioNombre())
            etTelefono.setText(sessionManager.getUsuarioTelefono())
            tvEmail.text = sessionManager.getUsuarioEmail()
            etImagenPerfil.setText(sessionManager.getUsuarioImagen() ?: "")
            ivPerfil.loadCircularImage(sessionManager.getUsuarioImagen())
        }
    }

    private fun setupViews() {
        // Botón cambiar foto - permite ingresar nueva URL
        binding.btnCambiarFoto.setOnClickListener {
            binding.tilImagenPerfil.requestFocus()
        }

        binding.btnGuardar.setOnClickListener {
            val nombre = binding.etNombre.text.toString().trim()
            val telefono = binding.etTelefono.text.toString().trim()
            val imagenUrl = binding.etImagenPerfil.text.toString().trim()

            if (validateFields(nombre, telefono)) {
                // Si el campo está vacío, usar foto genérica con iniciales
                val imagenPerfilUrl = if (imagenUrl.isEmpty()) {
                    "https://ui-avatars.com/api/?name=${nombre.replace(" ", "+")}&background=6200EE&color=fff&size=200"
                } else {
                    imagenUrl
                }

                val request = UsuarioActualizacionRequest(
                    nombre = nombre,
                    telefono = telefono,
                    imagenPerfilUrl = imagenPerfilUrl
                )

                val usuarioId = sessionManager.getUsuarioId()
                viewModel.actualizarPerfil(usuarioId, request)
            }
        }
    }

    private fun validateFields(nombre: String, telefono: String): Boolean {
        var isValid = true

        if (!ValidationUtils.isValidName(nombre)) {
            binding.tilNombre.error = getString(R.string.error_name_invalid)
            isValid = false
        } else {
            binding.tilNombre.error = null
        }

        if (!ValidationUtils.isValidPhone(telefono)) {
            binding.tilTelefono.error = getString(R.string.error_phone_invalid)
            isValid = false
        } else {
            binding.tilTelefono.error = null
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

        viewModel.usuarioActualizado.observe(this) { usuario ->
            sessionManager.actualizarUsuario(usuario)
            Snackbar.make(binding.root, "Perfil actualizado", Snackbar.LENGTH_SHORT).show()
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

