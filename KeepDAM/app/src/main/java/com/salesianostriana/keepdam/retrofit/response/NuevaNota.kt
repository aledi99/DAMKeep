package com.salesianostriana.keepdam.retrofit.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NuevaNota(
    @SerializedName("titulo")
    @Expose
    val titulo: String,
    @SerializedName("contenido")
    @Expose
    val contenido: String
)