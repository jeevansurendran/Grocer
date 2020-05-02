package com.silverpants.grocer.home

import androidx.lifecycle.liveData
import com.silverpants.grocer.data.network.WebApiClient
import com.silverpants.grocer.data.resource.Resource

object HomeRepository {
    val webApiService = WebApiClient.webApiService

    suspend fun getUserDetails() = webApiService.getUserDetails()

}