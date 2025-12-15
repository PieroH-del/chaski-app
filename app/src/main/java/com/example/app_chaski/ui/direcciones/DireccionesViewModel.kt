package com.example.app_chaski.ui.direcciones

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_chaski.data.models.DireccionDTO
import com.example.app_chaski.data.repository.DireccionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DireccionesViewModel @Inject constructor(
    private val direccionRepository: DireccionRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _direcciones = MutableLiveData<List<DireccionDTO>>()
    val direcciones: LiveData<List<DireccionDTO>> = _direcciones

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun cargarDirecciones(usuarioId: Long) {
        viewModelScope.launch {
            _loading.value = true
            direccionRepository.obtenerDireccionesPorUsuario(usuarioId)
                .onSuccess { data ->
                    _direcciones.value = data
                    _loading.value = false
                }
                .onFailure { exception ->
                    _error.value = exception.message
                    _loading.value = false
                }
        }
    }

    fun eliminarDireccion(direccionId: Long, usuarioId: Long) {
        viewModelScope.launch {
            _loading.value = true
            direccionRepository.eliminarDireccion(direccionId)
                .onSuccess {
                    cargarDirecciones(usuarioId)
                }
                .onFailure { exception ->
                    _error.value = exception.message
                    _loading.value = false
                }
        }
    }

    fun clearError() {
        _error.value = null
    }
}

