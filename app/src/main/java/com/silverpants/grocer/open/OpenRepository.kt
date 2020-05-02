package com.silverpants.grocer.open

import androidx.lifecycle.LiveData
import com.silverpants.grocer.auth.models.AuthResultModel
import com.silverpants.grocer.auth.models.UserModel
import com.silverpants.grocer.data.Converters.objectMapper
import com.silverpants.grocer.data.network.WebApiClient
import com.silverpants.grocer.data.resource.ApiResponse
import com.silverpants.grocer.data.resource.NetworkBoundResource
import com.silverpants.grocer.data.resource.Resource

/**
 * An [OpenRepository] object is responsible to return all the opening related requests from the network
 * and database.
 *
 * @author @jeevansurendran
 * @since 1.0
 */
object OpenRepository {
    val webApiService = WebApiClient.webApiService

    fun getUserRegister(idToken: String): LiveData<Resource<AuthResultModel>> {
        val data = objectMapper.createObjectNode()
        data.put("idToken", idToken)
        return object : NetworkBoundResource<AuthResultModel, AuthResultModel>() {
            override  fun createCall(): LiveData<ApiResponse<AuthResultModel>> =
                webApiService.guestRegister(data)
        }.asLiveData
    }

}