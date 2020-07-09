package com.silverpants.grocer.view.home

import com.silverpants.grocer.network.NetworkModule

object HomeRepository {
    val webApiService = NetworkModule.apiService

    suspend fun getUserDetails() = webApiService.getUserDetails()

}