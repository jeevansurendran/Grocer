package com.silverpants.grocer.network

import com.google.firebase.auth.FirebaseAuth
import com.silverpants.grocer.BuildConfig
import com.silverpants.grocer.data.Converters
import com.silverpants.grocer.misc.Constants
import com.silverpants.grocer.network.legacy.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import timber.log.Timber


object NetworkModule {
    var idToken: String? = null
    private fun provideClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()

        //this adds authorization header with token has token id cause that's the safest fucking shit
        addAuthorizationInterceptor(httpClientBuilder)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor { Timber.i(it) }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return httpClientBuilder.build()
    }

    private fun addAuthorizationInterceptor(httpClientBuilder: OkHttpClient.Builder) {
        httpClientBuilder.addInterceptor {
            val requestBuilder = it.request().newBuilder()
            if (idToken != null) {
                requestBuilder.addHeader("Authorization", "Bearer $idToken")
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

    val apiService: ApiService = retrofitInstance.create(
        ApiService::class.java
    )

    private fun provideAuthToken() {
        setupProvideAuthTokenListener()
    }

    private fun setupFreshToken() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.currentUser?.getIdToken(false)?.addOnSuccessListener {
            Timber.d("freshTokenDetected")
            idToken = it.token
        }
    }

    private fun setupProvideAuthTokenListener() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.addIdTokenListener { firebaseAuth: FirebaseAuth ->
            Timber.d("Token Change Detected")
            setupFreshToken()
        }
    }
}