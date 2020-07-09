package com.silverpants.grocer.network

import com.silverpants.grocer.data.auth.Model.TokenModel
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = NetworkModule.token?.accessToken

        return if (NetworkModule.token == null) {
            chain.proceed(chain.request())
        } else {
            val authenticatedRequest = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
            chain.proceed(authenticatedRequest)
        }
    }
}