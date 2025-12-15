package com.example.app_chaski.data.repository

import com.example.app_chaski.data.models.DireccionDTO
import com.example.app_chaski.data.models.DireccionRequest
import com.example.app_chaski.data.remote.ChaskiApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DireccionRepository @Inject constructor(
    private val apiService: ChaskiApiService
) {
    suspend fun obtenerDireccionesPorUsuario(usuarioId: Long): Result<List<DireccionDTO>> {
        return try {
            val response = apiService.obtenerDireccionesPorUsuario(usuarioId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun crearDireccion(request: DireccionRequest): Result<DireccionDTO> {
        return try {
            val response = apiService.crearDireccion(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun actualizarDireccion(direccionId: Long, request: DireccionRequest): Result<DireccionDTO> {
        return try {
            val response = apiService.actualizarDireccion(direccionId, request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun eliminarDireccion(direccionId: Long): Result<Unit> {
        return try {
            apiService.eliminarDireccion(direccionId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

