package com.example.app_chaski.data.repository

import com.example.app_chaski.data.models.RestauranteDTO
import com.example.app_chaski.data.remote.ChaskiApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestauranteRepository @Inject constructor(
    private val apiService: ChaskiApiService
) {
    suspend fun obtenerRestaurantes(): Result<List<RestauranteDTO>> {
        return try {
            val response = apiService.obtenerRestaurantes()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obtenerRestaurante(restauranteId: Long): Result<RestauranteDTO> {
        return try {
            val response = apiService.obtenerRestaurante(restauranteId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun buscarRestaurantes(nombre: String): Result<List<RestauranteDTO>> {
        return try {
            val response = apiService.buscarRestaurantes(nombre)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun filtrarPorDisponibilidad(estaAbierto: Boolean): Result<List<RestauranteDTO>> {
        return try {
            val response = apiService.filtrarRestaurantesPorDisponibilidad(estaAbierto)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun filtrarPorCategoria(categoriaId: Long): Result<List<RestauranteDTO>> {
        return try {
            val response = apiService.filtrarRestaurantesPorCategoria(categoriaId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

