package com.example.app_chaski.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_chaski.data.models.CategoriaDTO
import com.example.app_chaski.data.models.RestauranteDTO
import com.example.app_chaski.data.repository.CategoriaRepository
import com.example.app_chaski.data.repository.RestauranteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val restauranteRepository: RestauranteRepository,
    private val categoriaRepository: CategoriaRepository
) : ViewModel() {

    private val _restaurantes = MutableLiveData<List<RestauranteDTO>>()
    val restaurantes: LiveData<List<RestauranteDTO>> = _restaurantes

    private val _categorias = MutableLiveData<List<CategoriaDTO>>()
    val categorias: LiveData<List<CategoriaDTO>> = _categorias

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _categoriaSeleccionada = MutableLiveData<Long?>()
    val categoriaSeleccionada: LiveData<Long?> = _categoriaSeleccionada

    init {
        cargarCategorias()
        cargarRestaurantes()
    }

    fun cargarCategorias() {
        viewModelScope.launch {
            categoriaRepository.obtenerCategorias()
                .onSuccess { data ->
                    _categorias.value = data
                    Timber.d("Categorías cargadas: ${data.size}")
                }
                .onFailure { exception ->
                    Timber.e(exception, "Error cargando categorías")
                }
        }
    }

    fun cargarRestaurantes() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            restauranteRepository.obtenerRestaurantes()
                .onSuccess { data ->
                    _restaurantes.value = data
                    _loading.value = false
                    Timber.d("Restaurantes cargados: ${data.size}")
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Error al cargar restaurantes"
                    _loading.value = false
                    Timber.e(exception, "Error cargando restaurantes")
                }
        }
    }

    fun buscarRestaurantes(query: String) {
        if (query.isBlank()) {
            cargarRestaurantes()
            return
        }

        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            restauranteRepository.buscarRestaurantes(query)
                .onSuccess { data ->
                    _restaurantes.value = data
                    _loading.value = false
                }
                .onFailure { exception ->
                    _error.value = exception.message
                    _loading.value = false
                }
        }
    }

    fun filtrarSoloAbiertos() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            restauranteRepository.filtrarPorDisponibilidad(true)
                .onSuccess { data ->
                    _restaurantes.value = data
                    _loading.value = false
                }
                .onFailure { exception ->
                    _error.value = exception.message
                    _loading.value = false
                }
        }
    }

    fun filtrarPorCategoria(categoriaId: Long) {
        _categoriaSeleccionada.value = categoriaId
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            restauranteRepository.filtrarPorCategoria(categoriaId)
                .onSuccess { data ->
                    _restaurantes.value = data
                    _loading.value = false
                    Timber.d("Restaurantes filtrados por categoría $categoriaId: ${data.size}")
                }
                .onFailure { exception ->
                    _error.value = exception.message
                    _loading.value = false
                    Timber.e(exception, "Error filtrando por categoría")
                }
        }
    }

    fun limpiarFiltroCategoria() {
        _categoriaSeleccionada.value = null
        cargarRestaurantes()
    }

    fun clearError() {
        _error.value = null
    }
}

