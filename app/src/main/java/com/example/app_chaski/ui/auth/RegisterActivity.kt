package com.example.app_chaski.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.app_chaski.data.models.DireccionRequest
import com.example.app_chaski.data.repository.DireccionRepository
import com.example.app_chaski.databinding.ActivityRegisterBinding
import com.example.app_chaski.ui.main.MainActivity
import com.example.app_chaski.utils.SessionManager
import com.example.app_chaski.utils.ValidationUtils
import com.example.app_chaski.utils.hide
import com.example.app_chaski.utils.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var sessionManager: SessionManager

    @Inject
    lateinit var direccionRepository: DireccionRepository

    private var direccionPendiente: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        // El layout no tiene toolbar, comentar esta línea
        // binding.toolbar.setNavigationOnClickListener {
        //     finish()
        // }

        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.etNombre.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val telefono = binding.etTelefono.text.toString().trim()
            val direccion = binding.etDireccion.text.toString().trim()
            val imagenPerfil = binding.etImagenPerfil.text.toString().trim()

            if (validateFields(nombre, email, password, telefono, direccion)) {
                // Guardar dirección para crearla después del registro
                direccionPendiente = direccion

                // Si no proporciona foto, usar foto genérica
                val imagenUrl = imagenPerfil.ifEmpty {
                    "https://ui-avatars.com/api/?name=${nombre.replace(" ", "+")}&background=6200EE&color=fff&size=200"
                }
                viewModel.registro(nombre, email, password, telefono, direccion, imagenUrl)
            }
        }

        binding.tvLoginLink.setOnClickListener {
            finish()
        }
    }

    private fun validateFields(
        nombre: String,
        email: String,
        password: String,
        telefono: String,
        direccion: String
    ): Boolean {
        var isValid = true

        if (!ValidationUtils.isValidName(nombre)) {
            binding.tilNombre.error = "El nombre debe tener al menos 3 caracteres"
            isValid = false
        } else {
            binding.tilNombre.error = null
        }

        if (!ValidationUtils.isValidEmail(email)) {
            binding.tilEmail.error = "Correo inválido"
            isValid = false
        } else {
            binding.tilEmail.error = null
        }

        if (!ValidationUtils.isValidPassword(password)) {
            binding.tilPassword.error = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        } else {
            binding.tilPassword.error = null
        }

        if (!ValidationUtils.isValidPhone(telefono)) {
            binding.tilTelefono.error = "El teléfono debe tener 9 dígitos"
            isValid = false
        } else {
            binding.tilTelefono.error = null
        }

        if (direccion.isEmpty()) {
            binding.tilDireccion.error = "La dirección es obligatoria"
            isValid = false
        } else if (direccion.length < 10) {
            binding.tilDireccion.error = "Ingresa una dirección más completa"
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
                binding.btnRegistrar.isEnabled = false
            } else {
                binding.progressBar.hide()
                binding.btnRegistrar.isEnabled = true
            }
        }

        viewModel.usuario.observe(this) { usuario ->
            sessionManager.guardarSesion(usuario)

            // Crear dirección después del registro exitoso
            if (direccionPendiente.isNotEmpty()) {
                crearDireccionInicial(usuario.id, direccionPendiente)
            } else {
                navegarAMainActivity()
            }
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }

    private fun crearDireccionInicial(usuarioId: Long, direccion: String) {
        lifecycleScope.launch {
            binding.progressBar.show()

            val request = DireccionRequest(
                usuarioId = usuarioId,
                alias = "Casa",
                direccionCompleta = direccion,
                referencia = null,
                latitud = -12.0464, // Coordenadas por defecto (Lima, Perú)
                longitud = -77.0428,
                predeterminada = true
            )

            direccionRepository.crearDireccion(request)
                .onSuccess {
                    binding.progressBar.hide()
                    navegarAMainActivity()
                }
                .onFailure {
                    // Aunque falle la dirección, igual navegar (no es crítico)
                    binding.progressBar.hide()
                    navegarAMainActivity()
                }
        }
    }

    private fun navegarAMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

