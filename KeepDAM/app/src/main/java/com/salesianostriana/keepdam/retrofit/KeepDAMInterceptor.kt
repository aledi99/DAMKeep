package com.salesianostriana.keepdam.retrofit

import com.salesianostriana.keepdam.commons.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeepDAMInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val request: Request
        val token = SharedPreferencesManager().getSharedPreferences()
            .getString("api_key", "")

        val requestBuilder: Request.Builder =
            original.newBuilder().header("Authorization", "Bearer $token")
        request = requestBuilder.build()


        return chain.proceed(request)

    }
}