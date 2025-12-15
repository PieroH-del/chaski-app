package com.example.app_chaski.data.repository

import com.example.app_chaski.data.models.ProductoDTO
import com.example.app_chaski.data.remote.ChaskiApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductoRepository @Inject constructor(
    private val apiService: ChaskiApiService
) {
    suspend fun obtenerProductosPorRestaurante(restauranteId: Long): Result<List<ProductoDTO>> {
        return try {
            val response = apiService.obtenerProductosPorRestaurante(restauranteId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obtenerProductosDisponibles(restauranteId: Long): Result<List<ProductoDTO>> {
        return try {
            val response = apiService.obtenerProductosDisponibles(restauranteId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obtenerProducto(productoId: Long): Result<ProductoDTO> {
        return try {
            val response = apiService.obtenerProducto(productoId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

