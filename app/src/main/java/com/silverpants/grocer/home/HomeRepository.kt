package com.silverpants.grocer.home

import com.silverpants.grocer.network.NetworkModule

object HomeRepository {
    val webApiService = NetworkModule.webApiService

    suspend fun getUserDetails() = webApiService.getUserDetails()

}