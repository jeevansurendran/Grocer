package com.silverpants.grocer.view.auth

import androidx.lifecycle.LiveData
import com.silverpants.grocer.view.auth.models.AuthResultModel
import com.silverpants.grocer.data.Converters.objectMapper
import com.silverpants.grocer.network.NetworkModule
import com.silverpants.grocer.network.ApiService
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
    val API_SERVICE: ApiService = NetworkModule.apiService

    fun loginUser(phoneNumber: String, idToken: String ) : LiveData<Resource<AuthResultModel>> {
        val data = objectMapper.createObjectNode()
        data.put("phoneNumber", phoneNumber)
        data.put("idToken", idToken)
        return object : NetworkBoundResource<AuthResultModel, AuthResultModel>() {
            override  fun createCall(): LiveData<ApiResponse<AuthResultModel>> = API_SERVICE.postLogin(data)
        }.asLiveData
    }

    fun registerUser(displayName: String, phoneNumber: String, idToken: String): LiveData<Resource<AuthResultModel>> {
        val data = objectMapper.createObjectNode()
        data.put("displayName", displayName)
        data.put("phoneNumber", phoneNumber)
        data.put("idToken", idToken)
        return object : NetworkBoundResource<AuthResultModel, AuthResultModel>() {
            override  fun createCall(): LiveData<ApiResponse<AuthResultModel>> = API_SERVICE.postRegister(data)

        }.asLiveData
    }
}