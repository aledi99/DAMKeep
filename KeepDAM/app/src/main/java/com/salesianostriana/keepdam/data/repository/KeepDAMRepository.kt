package com.salesianostriana.keepdam.data.repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.salesianostriana.keepdam.di.MyApp
import com.salesianostriana.keepdam.retrofit.KeepDAMService
import com.salesianostriana.keepdam.retrofit.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeepDAMRepository @Inject constructor(var keepDAMService: KeepDAMService) {
    var allNota: MutableLiveData<List<NotaResponse>> = MutableLiveData()
    var oneNota: MutableLiveData<NotaResponse> = MutableLiveData()


    fun allTheNotas(): MutableLiveData<List<NotaResponse>> {
        val call: Call<List<NotaResponse>> = keepDAMService.allNota()
        call?.enqueue(object : Callback<List<NotaResponse>> {
            override fun onResponse(call: Call<List<NotaResponse>>, response: Response<List<NotaResponse>>) {
                if (response.isSuccessful) {
                    allNota.value = response.body()
                } else {
                    Toast.makeText(MyApp.instance, "Error de petición", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<NotaResponse>>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error de API", Toast.LENGTH_SHORT).show()
            }
        })

        return allNota
    }

    fun unaNota(id : String): MutableLiveData<NotaResponse> {
        val call: Call<NotaResponse>? = keepDAMService.oneNota(id)
        call?.enqueue(object : Callback<NotaResponse> {
            override fun onResponse(call: Call<NotaResponse>, response: Response<NotaResponse>) {
                if (response.isSuccessful) {
                    oneNota.value = response.body()
                } else {
                    Toast.makeText(MyApp.instance, "Error de petición", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NotaResponse>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error de API", Toast.LENGTH_SHORT).show()
            }
        })

        return oneNota
    }

}