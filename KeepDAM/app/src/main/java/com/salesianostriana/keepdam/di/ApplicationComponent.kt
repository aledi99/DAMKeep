package com.salesianostriana.keepdam.di

import com.salesianostriana.keepdam.*
import com.salesianostriana.keepdam.retrofit.KeepDAMClient
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [KeepDAMClient::class])
interface ApplicationComponent {
    fun inject(main: MainActivity)
    fun inject(detail: DetailActivity)
    fun inject(login: LoginActivity)
    fun inject(register: RegisterActivity)
    fun inject(notas: NotasFragment)
    fun inject(newNota : NewNotaActivity)
    fun inject(confirm : ConfirmActivity)
}