package com.example.app_chaski.data.repository

import com.example.app_chaski.data.models.PagoDTO
import com.example.app_chaski.data.models.PagoRequest
import com.example.app_chaski.data.remote.ChaskiApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PagoRepository @Inject constructor(
    private val apiService: ChaskiApiService
) {
    suspend fun crearPago(request: PagoRequest): Result<PagoDTO> {
        return try {
            val response = apiService.crearPago(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obtenerPagoPorPedido(pedidoId: Long): Result<PagoDTO> {
        return try {
            val response = apiService.obtenerPagoPorPedido(pedidoId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

