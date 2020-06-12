package com.salesianostriana.keepdam.retrofit

import com.salesianostriana.keepdam.retrofit.response.NotaResponse
import com.salesianostriana.keepdam.retrofit.response.NuevaNota
import retrofit2.Call
import retrofit2.http.*

interface KeepDAMService {

    @GET("/nota/")
    fun allNota() : Call<List<NotaResponse>>

    @GET("/nota/{id}")
    fun oneNota(@Path("id") id : String) : Call<NotaResponse>

    @POST("/nota/")
    fun newNota(@Body nota : NuevaNota) : Call<NotaResponse>

    @PUT("/nota/{id}")
    fun editNota(@Path("id") id : String, @Body nota: NuevaNota) : Call<NotaResponse>

    @DELETE("/nota/{id}")
    fun deleteNota(@Path("id") id : String) : Call<Void>

}