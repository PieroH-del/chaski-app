package com.example.app_chaski.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.app_chaski.databinding.ActivityLoginBinding
import com.example.app_chaski.ui.main.MainActivity
import com.example.app_chaski.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (validarCampos(email, password)) {
                viewModel.login(email, password)
            }
        }

        binding.tvRegistroLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validarCampos(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.etEmail.error = "El correo es obligatorio"
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Correo inválido"
            return false
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "La contraseña es obligatoria"
            return false
        }

        if (password.length < 6) {
            binding.etPassword.error = "La contraseña debe tener al menos 6 caracteres"
            return false
        }

        return true
    }

    private fun observeViewModel() {
        viewModel.loading.observe(this) { isLoading ->
            binding.btnLogin.isEnabled = !isLoading
            binding.btnLogin.text = if (isLoading) "Iniciando sesión..." else "Iniciar Sesión"
        }

        viewModel.usuario.observe(this) { usuario ->
            sessionManager.guardarSesion(usuario)
            Toast.makeText(this, "Bienvenido ${usuario.nombre}", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, "Error: $it", Toast.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }
}

