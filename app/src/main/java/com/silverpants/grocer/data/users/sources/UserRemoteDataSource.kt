package com.silverpants.grocer.data.users.sources

import com.silverpants.grocer.network.NetworkModule
import com.silverpants.grocer.network.ApiService

object UserRemoteDataSource {
    private val apiService: ApiService = NetworkModule.apiService
}