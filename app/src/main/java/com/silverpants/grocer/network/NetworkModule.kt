package com.silverpants.grocer.network

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.silverpants.grocer.BuildConfig
import com.silverpants.grocer.data.Converters
import com.silverpants.grocer.misc.Constants
import com.silverpants.grocer.network.legacy.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object NetworkModule {
    var idToken: String? = null
    private fun provideClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()

        //this adds authorization header with token has token id cause that's the safest fucking shit
        addAuthorizationInterceptor(httpClientBuilder)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor { Log.i("HTTP logging: ", it) }
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
        provideAuthToken()
        client(provideClient())
        baseUrl(Constants.baseUrl)
        addConverterFactory(JacksonConverterFactory.create(Converters.objectMapper))
        addCallAdapterFactory(LiveDataCallAdapterFactory())
    }.build()

    val webApiService: WebApiService = retrofitInstance.create(
        WebApiService::class.java
    )

    private fun provideAuthToken() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.currentUser?.getIdToken(false)
        setupProvideAuthTokenListener()
    }

    private fun setupProvideAuthTokenListener() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.addIdTokenListener { firebaseAuth: FirebaseAuth ->
            firebaseAuth.currentUser?.getIdToken(false)?.addOnSuccessListener {
                idToken = it.token
            }
        }
    }
}