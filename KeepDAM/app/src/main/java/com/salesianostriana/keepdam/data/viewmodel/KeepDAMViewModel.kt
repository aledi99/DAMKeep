package com.salesianostriana.keepdam.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.salesianostriana.keepdam.data.repository.KeepDAMRepository
import com.salesianostriana.keepdam.data.repository.UserRepository
import com.salesianostriana.keepdam.retrofit.response.*
import javax.inject.Inject

class KeepDAMViewModel @Inject constructor(keepDAMRepository: KeepDAMRepository) : ViewModel() {
    val repository = keepDAMRepository

    fun viewAllNota(): LiveData<List<NotaResponse>> {
        return repository.allTheNotas()
    }

    fun viewOneNota(data: String): LiveData<NotaResponse> {
        return repository.unaNota(data)
    }
}