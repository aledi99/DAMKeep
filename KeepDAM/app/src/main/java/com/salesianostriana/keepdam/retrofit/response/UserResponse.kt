package com.salesianostriana.keepdam.retrofit.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserResponse (

    @SerializedName("nick")
    @Expose
    val nick: String,
    @SerializedName("nombreCompleto")
    @Expose
    val nombreCompleto: String,
    @SerializedName("roles")
    @Expose
    val roles: String,
    @SerializedName("id")
    @Expose
    val id: String

)