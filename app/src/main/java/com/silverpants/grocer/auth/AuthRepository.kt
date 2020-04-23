package com.silverpants.grocer.auth

import androidx.lifecycle.LiveData
import com.silverpants.grocer.auth.models.AuthResultModel
import com.silverpants.grocer.auth.models.UserModel
import com.silverpants.grocer.data.Converters.objectMapper
import com.silverpants.grocer.data.network.WebApiClient
import com.silverpants.grocer.data.network.WebApiService
import com.silverpants.grocer.data.resource.ApiResponse
import com.silverpants.grocer.data.resource.NetworkBoundResource
import com.silverpants.grocer.data.resource.Resource

object AuthRepository {
    val webApiService: WebApiService = WebApiClient.webApiService

    fun loginUser(phoneNumber: String, idToken: String ) : LiveData<Resource<AuthResultModel>> {
        val data = objectMapper.createObjectNode()
        data.put("phoneNumber", phoneNumber)
        data.put("idToken", idToken)
        return object : NetworkBoundResource<AuthResultModel, AuthResultModel>() {
            override fun createCall(): LiveData<ApiResponse<AuthResultModel>> = webApiService.login(data)
        }.asLiveData
    }

    fun registerUser(displayName: String, phoneNumber: String, idToken: String): LiveData<Resource<AuthResultModel>> {
        val data = objectMapper.createObjectNode()
        data.put("displayName", displayName)
        data.put("phoneNumber", phoneNumber)
        data.put("idToken", idToken)
        return object : NetworkBoundResource<AuthResultModel, AuthResultModel>() {
            override fun createCall(): LiveData<ApiResponse<AuthResultModel>> = webApiService.register(data)

        }.asLiveData
    }
}