package com.example.app_chaski.data.repository

import com.example.app_chaski.data.models.CategoriaDTO
import com.example.app_chaski.data.remote.ChaskiApiService
import javax.inject.Inject

class CategoriaRepository @Inject constructor(
    private val apiService: ChaskiApiService
) {
    suspend fun obtenerCategorias(): Result<List<CategoriaDTO>> {
        return try {
            val response = apiService.obtenerCategorias()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obtenerCategoria(id: Long): Result<CategoriaDTO> {
        return try {
            val response = apiService.obtenerCategoria(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

