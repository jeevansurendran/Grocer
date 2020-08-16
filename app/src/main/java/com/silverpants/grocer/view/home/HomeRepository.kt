package com.silverpants.grocer.view.home

import com.silverpants.grocer.hardware.network.RamenApiService
import javax.inject.Inject

class HomeRepository @Inject constructor(private val webApiService: RamenApiService) {

    suspend fun getUserDetails() = webApiService.getUserDetails()

}