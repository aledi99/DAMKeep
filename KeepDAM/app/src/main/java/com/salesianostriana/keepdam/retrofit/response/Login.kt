package com.salesianostriana.keepdam.retrofit.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class Login (
    var username: String,
    var password: String
)