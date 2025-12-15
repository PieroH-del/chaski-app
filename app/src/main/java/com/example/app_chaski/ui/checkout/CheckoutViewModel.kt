package com.example.app_chaski.ui.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_chaski.data.models.*
import com.example.app_chaski.data.repository.DireccionRepository
import com.example.app_chaski.data.repository.PagoRepository
import com.example.app_chaski.data.repository.PedidoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val direccionRepository: DireccionRepository,
    private val pedidoRepository: PedidoRepository,
    private val pagoRepository: PagoRepository
) : ViewModel() {

    private val _direcciones = MutableLiveData<List<DireccionDTO>>()
    val direcciones: LiveData<List<DireccionDTO>> = _direcciones

    private val _pedidoCreado = MutableLiveData<PedidoDTO>()
    val pedidoCreado: LiveData<PedidoDTO> = _pedidoCreado

    private val _pagoCompletado = MutableLiveData<PagoDTO>()
    val pagoCompletado: LiveData<PagoDTO> = _pagoCompletado

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

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

    fun crearPedido(pedidoRequest: PedidoRequest) {
        viewModelScope.launch {
            _loading.value = true

            pedidoRepository.crearPedido(pedidoRequest)
                .onSuccess { pedido ->
                    _pedidoCreado.value = pedido
                    Timber.d("Pedido creado: ${pedido.id}")
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Error al crear pedido"
                    _loading.value = false
                    Timber.e(exception, "Error creando pedido")
                }
        }
    }

    fun crearPago(pagoRequest: PagoRequest) {
        viewModelScope.launch {
            pagoRepository.crearPago(pagoRequest)
                .onSuccess { pago ->
                    _pagoCompletado.value = pago
                    _loading.value = false
                    Timber.d("Pago completado: ${pago.id}")
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Error al procesar pago"
                    _loading.value = false
                    Timber.e(exception, "Error procesando pago")
                }
        }
    }

    fun clearError() {
        _error.value = null
    }
}

