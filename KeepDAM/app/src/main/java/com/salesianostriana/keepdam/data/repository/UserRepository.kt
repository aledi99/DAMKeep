package com.salesianostriana.keepdam.data.repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.salesianostriana.keepdam.commons.SharedPreferencesManager
import com.salesianostriana.keepdam.di.MyApp
import com.salesianostriana.keepdam.retrofit.UserService
import com.salesianostriana.keepdam.retrofit.response.Login
import com.salesianostriana.keepdam.retrofit.response.LoginResponse
import com.salesianostriana.keepdam.retrofit.response.Register
import com.salesianostriana.keepdam.retrofit.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(var userService : UserService) {
    var token: MutableLiveData<LoginResponse> = MutableLiveData()
    var newUser: MutableLiveData<UserResponse> = MutableLiveData()


    fun register(data: Register): MutableLiveData<UserResponse> {
        val call: Call<UserResponse>? = userService.register(data)
        call?.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    newUser.value = response.body()
                } else {
                    Toast.makeText(MyApp.instance, "Error de petición", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error de API", Toast.LENGTH_SHORT).show()
            }
        })

        return newUser
    }

    fun login(data: Login): MutableLiveData<LoginResponse> {
        val call: Call<LoginResponse>? = userService.login(data)
        call?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    token.value = response.body()
                    SharedPreferencesManager().setStringValue(
                        "api_key", token.value!!.token
                    )
                } else {
                    Toast.makeText(MyApp.instance, "Error de petición", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error de API", Toast.LENGTH_SHORT).show()
            }
        })

        return token
    }

}