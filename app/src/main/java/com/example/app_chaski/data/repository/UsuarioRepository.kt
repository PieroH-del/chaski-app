package com.example.app_chaski.data.repository

import com.example.app_chaski.data.models.*
import com.example.app_chaski.data.remote.ChaskiApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioRepository @Inject constructor(
    private val apiService: ChaskiApiService
) {
    suspend fun registrarUsuario(request: UsuarioRegistroRequest): Result<UsuarioDTO> {
        return try {
            val response = apiService.registrarUsuario(request)
            Result.success(response)
        } catch (e: Exception) {
            val errorMessage = when {
                e.message?.contains("409") == true -> "El correo electrónico ya está registrado"
                e.message?.contains("400") == true -> "Datos inválidos. Verifique la información"
                e.message?.contains("500") == true -> "Error del servidor. Intente más tarde"
                e.message?.contains("Unable to resolve host") == true -> "Sin conexión a Internet"
                e.message?.contains("timeout") == true -> "Tiempo de espera agotado"
                else -> "Error de conexión: ${e.message}"
            }
            Result.failure(Exception(errorMessage))
        }
    }

    suspend fun loginUsuario(request: LoginRequest): Result<UsuarioDTO> {
        return try {
            val response = apiService.loginUsuario(request)
            Result.success(response)
        } catch (e: Exception) {
            val errorMessage = when {
                e.message?.contains("401") == true || e.message?.contains("400") == true -> "Usuario o contraseña incorrectos"
                e.message?.contains("404") == true -> "Usuario no encontrado"
                e.message?.contains("500") == true -> "Error del servidor. Intente más tarde"
                e.message?.contains("Unable to resolve host") == true -> "Sin conexión a Internet"
                e.message?.contains("timeout") == true -> "Tiempo de espera agotado"
                else -> "Error de conexión: ${e.message}"
            }
            Result.failure(Exception(errorMessage))
        }
    }

    suspend fun obtenerUsuario(usuarioId: Long): Result<UsuarioDTO> {
        return try {
            val response = apiService.obtenerUsuario(usuarioId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun actualizarUsuario(usuarioId: Long, request: UsuarioActualizacionRequest): Result<UsuarioDTO> {
        return try {
            val response = apiService.actualizarUsuario(usuarioId, request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

