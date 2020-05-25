package com.silverpants.grocer.home

import com.silverpants.grocer.network.WebApiClient

object HomeRepository {
    val webApiService = WebApiClient.webApiService

    suspend fun getUserDetails() = webApiService.getUserDetails()

}