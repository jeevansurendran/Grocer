package com.silverpants.grocer.data.network

import com.silverpants.grocer.data.Converters
import com.silverpants.grocer.misc.Constants
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object WebApiClient {
    private val retrofitInstance = Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(JacksonConverterFactory.create(Converters.objectMapper))
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()

    val webApiService = retrofitInstance.create(WebApiService::class.java);
}