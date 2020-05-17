package com.salesianostriana.keepdam.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.salesianostriana.keepdam.data.repository.UserRepository
import com.salesianostriana.keepdam.retrofit.response.Login
import com.salesianostriana.keepdam.retrofit.response.LoginResponse
import com.salesianostriana.keepdam.retrofit.response.Register
import com.salesianostriana.keepdam.retrofit.response.UserResponse
import javax.inject.Inject

class UserViewModel @Inject constructor(userRepository: UserRepository) : ViewModel() {
    val repository = userRepository

    fun viewLogin(data: Login): LiveData<LoginResponse> {
        return repository.login(data)
    }

    fun viewRegister(data: Register): LiveData<UserResponse> {
        return repository.register(data)
    }
}