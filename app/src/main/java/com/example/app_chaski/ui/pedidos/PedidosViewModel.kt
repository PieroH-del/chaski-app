package com.example.app_chaski.ui.pedidos

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
class PedidosViewModel @Inject constructor(
    private val pedidoRepository: PedidoRepository
) : ViewModel() {

    private val _pedidos = MutableLiveData<List<PedidoDTO>>()
    val pedidos: LiveData<List<PedidoDTO>> = _pedidos

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun cargarPedidos(usuarioId: Long) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            pedidoRepository.obtenerPedidosPorUsuario(usuarioId)
                .onSuccess { data ->
                    _pedidos.value = data.sortedByDescending { it.fechaCreacion }
                    _loading.value = false
                    Timber.d("Pedidos cargados: ${data.size}")
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Error al cargar pedidos"
                    _loading.value = false
                    Timber.e(exception, "Error cargando pedidos")
                }
        }
    }

    fun cancelarPedido(pedidoId: Long) {
        viewModelScope.launch {
            _loading.value = true

            pedidoRepository.cancelarPedido(pedidoId)
                .onSuccess { pedidoActualizado ->
                    // Actualizar la lista
                    val listaActual = _pedidos.value?.toMutableList() ?: mutableListOf()
                    val index = listaActual.indexOfFirst { it.id == pedidoId }
                    if (index != -1) {
                        listaActual[index] = pedidoActualizado
                        _pedidos.value = listaActual
                    }
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

