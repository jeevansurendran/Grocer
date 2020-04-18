package com.silverpants.grocer.auth

import androidx.lifecycle.LiveData
import com.silverpants.grocer.auth.models.UserModel
import com.silverpants.grocer.data.Converters.objectMapper
import com.silverpants.grocer.data.network.WebApiClient
import com.silverpants.grocer.data.network.WebApiService
import com.silverpants.grocer.data.resource.ApiResponse
import com.silverpants.grocer.data.resource.NetworkBoundResource
import com.silverpants.grocer.data.resource.Resource

object AuthRepository {
    val webApiService: WebApiService = WebApiClient.webApiService

    fun registerUser(displayName: String, phoneNumber: String): LiveData<Resource<UserModel>> {
        val data = objectMapper.createObjectNode()
        data.put("displayName", displayName)
        data.put("phoneNumber", phoneNumber)
        return object : NetworkBoundResource<UserModel, UserModel>() {
            override fun createCall(): LiveData<ApiResponse<UserModel>> =
                webApiService.register(data)

        }.asLiveData
    }
}