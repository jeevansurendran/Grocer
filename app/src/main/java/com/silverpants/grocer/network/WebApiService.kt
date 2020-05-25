package com.silverpants.grocer.network

import androidx.lifecycle.LiveData
import com.fasterxml.jackson.databind.node.ObjectNode
import com.silverpants.grocer.auth.models.AuthResultModel
import com.silverpants.grocer.auth.models.UserModel
import com.silverpants.grocer.network.legacy.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WebApiService {
    @POST("users/register")
    fun register(@Body userCredentials: ObjectNode): LiveData<ApiResponse<AuthResultModel>>

    @POST("users/login")
    fun login(@Body userCredentials: ObjectNode): LiveData<ApiResponse<AuthResultModel>>

    @POST("/users/guest/register")
    fun guestRegister(@Body userCredentials: ObjectNode): LiveData<ApiResponse<AuthResultModel>>

    @GET("users/me")
    suspend fun getUserDetails(): UserModel
}