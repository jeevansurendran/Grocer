package com.silverpants.grocer.auth

import androidx.lifecycle.LiveData
import com.silverpants.grocer.auth.models.AuthResultModel
import com.silverpants.grocer.data.Converters.objectMapper
import com.silverpants.grocer.network.WebApiClient
import com.silverpants.grocer.network.WebApiService
import com.silverpants.grocer.network.legacy.ApiResponse
import com.silverpants.grocer.network.legacy.NetworkBoundResource
import com.silverpants.grocer.network.legacy.Resource

/**
 * An [AuthRepository] object is responsible to return all the authentication related requests from the network
 * and database.
 *
 * @author @jeevansurendran
 * @since 1.0
 */
object AuthRepository {
    val webApiService: WebApiService = WebApiClient.webApiService

    fun loginUser(phoneNumber: String, idToken: String ) : LiveData<Resource<AuthResultModel>> {
        val data = objectMapper.createObjectNode()
        data.put("phoneNumber", phoneNumber)
        data.put("idToken", idToken)
        return object : NetworkBoundResource<AuthResultModel, AuthResultModel>() {
            override  fun createCall(): LiveData<ApiResponse<AuthResultModel>> = webApiService.login(data)
        }.asLiveData
    }

    fun registerUser(displayName: String, phoneNumber: String, idToken: String): LiveData<Resource<AuthResultModel>> {
        val data = objectMapper.createObjectNode()
        data.put("displayName", displayName)
        data.put("phoneNumber", phoneNumber)
        data.put("idToken", idToken)
        return object : NetworkBoundResource<AuthResultModel, AuthResultModel>() {
            override  fun createCall(): LiveData<ApiResponse<AuthResultModel>> = webApiService.register(data)

        }.asLiveData
    }
}