package com.silverpants.grocer.data.network

import com.silverpants.grocer.misc.Constants
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object WebApiClient {
    private val retrofitInstance = Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

    val webApiService = retrofitInstance.create(WebApiService::class.java);
}