package com.silverpants.grocer.data.network

import android.util.Log
import com.silverpants.grocer.BuildConfig
import com.silverpants.grocer.data.Converters
import com.silverpants.grocer.misc.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object WebApiClient {
    var idToken: String? = null
    private fun provideClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()

        //this adds authorization header with token has token id cause that's the safest fucking shit
        addAuthorizationInterceptor(httpClientBuilder)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor { Log.e("HTTP logging: ", it) }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return httpClientBuilder.build()
    }

    private fun addAuthorizationInterceptor(httpClientBuilder: OkHttpClient.Builder) {
        httpClientBuilder.addInterceptor {
            val requestBuilder = it.request().newBuilder()
            if (idToken != null) {
                requestBuilder.addHeader("Authorization", "idToken : $idToken")
            }
            val request = requestBuilder.build()
            it.proceed(request)
        }
    }

    private val retrofitInstance = Retrofit.Builder().apply {
        client(provideClient())
        baseUrl(Constants.baseUrl)
        addConverterFactory(JacksonConverterFactory.create(Converters.objectMapper))
        addCallAdapterFactory(LiveDataCallAdapterFactory())
    }.build()

    val webApiService: WebApiService = retrofitInstance.create(WebApiService::class.java)
}