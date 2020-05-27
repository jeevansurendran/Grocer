package com.silverpants.grocer.data.users.sources

import com.silverpants.grocer.auth.models.AuthResultModel
import com.silverpants.grocer.data.Converters
import com.silverpants.grocer.data.users.models.UserModel
import com.silverpants.grocer.network.WebApiClient
import com.silverpants.grocer.network.WebApiService

object UserRemoteDataSource {
    private val apiService: WebApiService = WebApiClient.webApiService

    suspend fun guestRegister(idToken: String): UserModel {
        val data = Converters.objectMapper.createObjectNode()
        data.put("idToken", idToken)
        return apiService.guestRegister(data);
    }
}