package com.example.app_chaski.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_chaski.data.models.LoginRequest
import com.example.app_chaski.data.models.UsuarioDTO
import com.example.app_chaski.data.models.UsuarioRegistroRequest
import com.example.app_chaski.data.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _usuario = MutableLiveData<UsuarioDTO>()
    val usuario: LiveData<UsuarioDTO> = _usuario

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            usuarioRepository.loginUsuario(LoginRequest(email, password))
                .onSuccess { data ->
                    _usuario.value = data
                    _loading.value = false
                    Timber.d("Login exitoso: ${data.nombre}")
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Error al iniciar sesión"
                    _loading.value = false
                    Timber.e(exception, "Error en login")
                }
        }
    }

    fun registro(
        nombre: String,
        email: String,
        password: String,
        telefono: String,
        direccion: String,
        imagenPerfilUrl: String
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val request = UsuarioRegistroRequest(
                nombre = nombre,
                email = email,
                password = password,
                telefono = telefono,
                imagenPerfilUrl = imagenPerfilUrl
            )

            usuarioRepository.registrarUsuario(request)
                .onSuccess { data ->
                    _usuario.value = data
                    _loading.value = false
                    Timber.d("Registro exitoso: ${data.nombre}")
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Error al registrarse"
                    _loading.value = false
                    Timber.e(exception, "Error en registro")
                }
        }
    }

    fun clearError() {
        _error.value = null
    }
}

