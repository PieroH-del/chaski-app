package com.example.app_chaski.ui.direcciones

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_chaski.data.models.DireccionDTO
import com.example.app_chaski.data.models.DireccionRequest
import com.example.app_chaski.data.repository.DireccionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AgregarDireccionViewModel @Inject constructor(
    private val direccionRepository: DireccionRepository
) : ViewModel() {

    private val _direccionGuardada = MutableLiveData<DireccionDTO>()
    val direccionGuardada: LiveData<DireccionDTO> = _direccionGuardada

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun guardarDireccion(request: DireccionRequest) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            direccionRepository.crearDireccion(request)
                .onSuccess { data ->
                    _direccionGuardada.value = data
                    _loading.value = false
                    Timber.d("Dirección guardada: ${data.alias}")
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Error al guardar dirección"
                    _loading.value = false
                    Timber.e(exception, "Error guardando dirección")
                }
        }
    }

    fun actualizarDireccion(direccionId: Long, request: DireccionRequest) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            direccionRepository.actualizarDireccion(direccionId, request)
                .onSuccess { data ->
                    _direccionGuardada.value = data
                    _loading.value = false
                    Timber.d("Dirección actualizada: ${data.alias}")
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Error al actualizar dirección"
                    _loading.value = false
                    Timber.e(exception, "Error actualizando dirección")
                }
        }
    }

    fun clearError() {
        _error.value = null
    }
}

