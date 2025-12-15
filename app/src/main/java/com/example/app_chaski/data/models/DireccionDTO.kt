package com.example.app_chaski.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DireccionDTO(
    val id: Long,
    val usuarioId: Long,

    @SerializedName("etiqueta")
    val alias: String?,

    val direccionCompleta: String,
    val referencia: String?,
    val latitud: Double,
    val longitud: Double,

    @SerializedName("esPredeterminada")
    val predeterminada: Boolean
) : Parcelable

data class DireccionRequest(
    val usuarioId: Long,

    @SerializedName("etiqueta")
    val alias: String,

    val direccionCompleta: String,
    val referencia: String?,
    val latitud: Double,
    val longitud: Double,

    @SerializedName("esPredeterminada")
    val predeterminada: Boolean
)

