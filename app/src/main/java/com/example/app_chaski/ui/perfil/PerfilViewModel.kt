package com.example.app_chaski.ui.perfil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_chaski.data.models.UsuarioActualizacionRequest
import com.example.app_chaski.data.models.UsuarioDTO
import com.example.app_chaski.data.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _usuarioActualizado = MutableLiveData<UsuarioDTO>()
    val usuarioActualizado: LiveData<UsuarioDTO> = _usuarioActualizado

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun actualizarPerfil(usuarioId: Long, request: UsuarioActualizacionRequest) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            usuarioRepository.actualizarUsuario(usuarioId, request)
                .onSuccess { data ->
                    _usuarioActualizado.value = data
                    _loading.value = false
                    Timber.d("Perfil actualizado: ${data.nombre}")
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Error al actualizar perfil"
                    _loading.value = false
                    Timber.e(exception, "Error actualizando perfil")
                }
        }
    }

    fun clearError() {
        _error.value = null
    }
}

