package com.silverpants.grocer.data.network

import androidx.lifecycle.LiveData
import com.fasterxml.jackson.databind.node.ObjectNode
import com.silverpants.grocer.auth.models.AuthResultModel
import com.silverpants.grocer.auth.models.UserModel
import com.silverpants.grocer.data.resource.ApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface WebApiService {
    @POST("users/register")
    fun register(@Body userCredentials: ObjectNode) : LiveData<ApiResponse<AuthResultModel>>

    @POST("users/login")
    fun login(@Body userCredentials: ObjectNode) : LiveData<ApiResponse<AuthResultModel>>
}