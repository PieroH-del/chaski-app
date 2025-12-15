package com.example.app_chaski.ui.productos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_chaski.data.models.ProductoDTO
import com.example.app_chaski.data.repository.ProductoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductosViewModel @Inject constructor(
    private val productoRepository: ProductoRepository
) : ViewModel() {

    private val _productos = MutableLiveData<List<ProductoDTO>>()
    val productos: LiveData<List<ProductoDTO>> = _productos

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun cargarProductos(restauranteId: Long) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            productoRepository.obtenerProductosDisponibles(restauranteId)
                .onSuccess { data ->
                    _productos.value = data
                    _loading.value = false
                    Timber.d("Productos cargados: ${data.size}")
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Error al cargar productos"
                    _loading.value = false
                    Timber.e(exception, "Error cargando productos")
                }
        }
    }

    fun clearError() {
        _error.value = null
    }
}

