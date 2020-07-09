package com.silverpants.grocer.data.auth.sources

import com.silverpants.grocer.data.Converters
import com.silverpants.grocer.data.auth.Model.TokenModel
import com.silverpants.grocer.network.ApiService
import com.silverpants.grocer.network.NetworkModule

object AuthRemoteDataSource {
    private val apiService: ApiService = NetworkModule.apiService

    suspend fun postGuestRegister(idToken: String): TokenModel {
        val data = Converters.objectMapper.createObjectNode()
        data.put("idToken", idToken)
        return apiService.postGuestRegister(data)
    }

    suspend fun postGuestLogin(idToken: String): TokenModel {
        val data = Converters.objectMapper.createObjectNode()
        data.put("idToken", idToken)
        return apiService.postGuestLogin(data)
    }

    suspend fun postRefreshToken(refreshToken: String): TokenModel {
        val data = Converters.objectMapper.createObjectNode()
        data.put("refreshToken", refreshToken)
        return apiService.postRefreshToken(data)
    }

    suspend fun postLogout(refreshToken: String) {
        val data = Converters.objectMapper.createObjectNode()
        data.put("refreshToken", refreshToken)
        return apiService.postLogout(data)
    }
}