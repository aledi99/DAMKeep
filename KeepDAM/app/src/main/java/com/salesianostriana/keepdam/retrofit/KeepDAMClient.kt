package com.salesianostriana.keepdam.retrofit


import com.salesianostriana.keepdam.commons.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class KeepDAMClient {

    @Singleton
    @Provides
    @Named("url")
    fun provideBaseUrl(): String = Constants.API_BASE_URL


    @Singleton
    @Provides
    fun provideOkHttpClient(keepDAMInterceptor: KeepDAMInterceptor): OkHttpClient {
        return with(OkHttpClient.Builder()) {
            addInterceptor(keepDAMInterceptor)
            connectTimeout(Constants.TIMEOUT_INMILIS, TimeUnit.MILLISECONDS)
            build()
        }
    }

    @Singleton
    @Provides
    fun provideKeepDAMService(@Named("url") baseUrl: String, okHttpClient: OkHttpClient): KeepDAMService {
        var logging : HttpLoggingInterceptor = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(KeepDAMInterceptor()).addInterceptor(logging).build())
            .build()
            .create(KeepDAMService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserService(@Named("url") baseUrl: String, okHttpClient: OkHttpClient): UserService {
        var logging : HttpLoggingInterceptor = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(KeepDAMInterceptor()).addInterceptor(logging).build())
            .build()
            .create(UserService::class.java)
    }

}