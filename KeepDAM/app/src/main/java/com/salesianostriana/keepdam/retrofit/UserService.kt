package com.salesianostriana.keepdam.retrofit

import com.salesianostriana.keepdam.retrofit.response.Login
import com.salesianostriana.keepdam.retrofit.response.LoginResponse
import com.salesianostriana.keepdam.retrofit.response.Register
import com.salesianostriana.keepdam.retrofit.response.UserResponse
import dagger.Provides
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("/auth/login")
    fun login(@Body login : Login) : Call<LoginResponse>

    @POST("/user/")
    fun register(@Body register : Register) : Call<UserResponse>
}