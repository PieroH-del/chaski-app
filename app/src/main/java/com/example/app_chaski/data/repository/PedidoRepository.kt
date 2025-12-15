package com.example.app_chaski.data.repository

import com.example.app_chaski.data.models.*
import com.example.app_chaski.data.remote.ChaskiApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PedidoRepository @Inject constructor(
    private val apiService: ChaskiApiService
) {
    suspend fun crearPedido(request: PedidoRequest): Result<PedidoDTO> {
        return try {
            val response = apiService.crearPedido(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obtenerPedidosPorUsuario(usuarioId: Long): Result<List<PedidoDTO>> {
        return try {
            val response = apiService.obtenerPedidosPorUsuario(usuarioId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obtenerPedido(pedidoId: Long): Result<PedidoDTO> {
        return try {
            val response = apiService.obtenerPedido(pedidoId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun cancelarPedido(pedidoId: Long): Result<PedidoDTO> {
        return try {
            val response = apiService.cancelarPedido(pedidoId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

