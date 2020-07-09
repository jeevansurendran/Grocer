package com.silverpants.grocer.network

import com.google.firebase.auth.FirebaseAuth
import com.silverpants.grocer.BuildConfig
import com.silverpants.grocer.data.Converters
import com.silverpants.grocer.data.auth.Model.TokenModel
import com.silverpants.grocer.misc.Constants
import com.silverpants.grocer.network.legacy.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import timber.log.Timber


object NetworkModule {
    var token: TokenModel? = null

    private fun provideClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()

        //this adds authorization header with token has token id cause that's the safest fucking shit
        httpClientBuilder.addInterceptor(AccessTokenInterceptor())
        httpClientBuilder.authenticator(AccessTokenAuthenticator())
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor { Timber.i(it) }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return httpClientBuilder.build()
    }

    private val retrofitInstance = Retrofit.Builder().apply {
        client(provideClient())
        baseUrl(Constants.baseUrl)
        addConverterFactory(JacksonConverterFactory.create(Converters.objectMapper))
        addCallAdapterFactory(LiveDataCallAdapterFactory())
    }.build()

    val apiService: ApiService = retrofitInstance.create(
        ApiService::class.java
    )
}