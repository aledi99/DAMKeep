package com.salesianostriana.keepdam.retrofit.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Register (
    @SerializedName("nick")
    @Expose
    var nick: String,
    @SerializedName("fullname")
    @Expose
    var fullname: String,
    @SerializedName("password")
    @Expose
    var password: String,
    @SerializedName("password2")
    @Expose
    var password2: String
    )