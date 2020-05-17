package com.salesianostriana.keepdam.retrofit.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class LoginResponse (
    @SerializedName("token")
    @Expose
    var token: String,
    @SerializedName("refreshToken")
    @Expose
    var refreshToken: String,
    @SerializedName("user")
    @Expose
    var user: UserResponse
)