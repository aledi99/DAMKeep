package com.salesianostriana.keepdam.retrofit.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class NotaResponse(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("titulo")
    @Expose
    val titulo: String,
    @SerializedName("contenido")
    @Expose
    val contenido: String,
    @SerializedName("fechaCreacion")
    @Expose
    val fechaCreacion: String,
    @SerializedName("ultimaEdicion")
    @Expose
    val ultimaEdicion: String,
    @SerializedName("autor")
    @Expose
    val autor: UserResponse
)