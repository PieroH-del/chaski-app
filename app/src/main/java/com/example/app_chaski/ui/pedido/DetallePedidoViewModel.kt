package com.example.app_chaski.ui.pedido

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_chaski.data.models.PedidoDTO
import com.example.app_chaski.data.repository.PedidoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetallePedidoViewModel @Inject constructor(
    private val pedidoRepository: PedidoRepository
) : ViewModel() {

    private val _pedido = MutableLiveData<PedidoDTO>()
    val pedido: LiveData<PedidoDTO> = _pedido

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun cargarDetallePedido(pedidoId: Long) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            pedidoRepository.obtenerPedido(pedidoId)
                .onSuccess { data ->
                    _pedido.value = data
                    _loading.value = false
                    Timber.d("Pedido cargado: ${data.id}")
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Error al cargar pedido"
                    _loading.value = false
                    Timber.e(exception, "Error cargando pedido")
                }
        }
    }

    fun cancelarPedido(pedidoId: Long) {
        viewModelScope.launch {
            _loading.value = true

            pedidoRepository.cancelarPedido(pedidoId)
                .onSuccess { pedidoActualizado ->
                    _pedido.value = pedidoActualizado
                    _loading.value = false
                    Timber.d("Pedido cancelado: $pedidoId")
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Error al cancelar pedido"
                    _loading.value = false
                    Timber.e(exception, "Error cancelando pedido")
                }
        }
    }

    fun clearError() {
        _error.value = null
    }
}

