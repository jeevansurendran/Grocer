package com.silverpants.grocer.network

import androidx.lifecycle.LiveData
import com.fasterxml.jackson.databind.node.ObjectNode
import com.silverpants.grocer.auth.models.AuthResultModel
import com.silverpants.grocer.data.shops.models.ShopModel
import com.silverpants.grocer.data.users.models.UserModel
import com.silverpants.grocer.network.legacy.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WebApiService {
    @POST("/users/register")
    fun register(@Body userCredentials: ObjectNode): LiveData<ApiResponse<AuthResultModel>>

    @POST("/users/login")
    fun login(@Body userCredentials: ObjectNode): LiveData<ApiResponse<AuthResultModel>>

    @POST("/users/guest/register")
    suspend fun guestRegister(@Body userCredentials: ObjectNode): UserModel

    @GET("/users/me")
    suspend fun getUserDetails(): UserModel

    @GET("/shops/")
    suspend fun getShop(@Path("pk") pk: Long): ShopModel

    @GET("/shops")
    suspend fun getAllShops(): List<ShopModel>

    @GET("/shops")
    suspend fun getNearbyShops(): List<ShopModel>

}