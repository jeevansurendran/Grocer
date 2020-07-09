package com.silverpants.grocer.network

import androidx.lifecycle.LiveData
import com.fasterxml.jackson.databind.node.ObjectNode
import com.silverpants.grocer.view.auth.models.AuthResultModel
import com.silverpants.grocer.data.auth.Model.TokenModel
import com.silverpants.grocer.data.shops.models.ShopModel
import com.silverpants.grocer.data.users.models.UserModel
import com.silverpants.grocer.network.legacy.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/users/register")
    fun postRegister(@Body userCredentials: ObjectNode): LiveData<ApiResponse<AuthResultModel>>

    @POST("/users/login")
    fun postLogin(@Body userCredentials: ObjectNode): LiveData<ApiResponse<AuthResultModel>>

    /* Authentication */
    @POST("/auth/guests/register")
    suspend fun postGuestRegister(@Body userCredentials: ObjectNode): TokenModel

    @POST("/auth/guests/login")
    suspend fun postGuestLogin(@Body userCredentials: ObjectNode) : TokenModel

    @POST("/auth/token")
    suspend fun postRefreshToken(@Body refreshToken : ObjectNode) : TokenModel

    @POST("/auth/logout")
    suspend fun  postLogout(@Body refreshToken: ObjectNode)

    /* Users */
    @GET("/users/me")
    suspend fun getUserDetails(): UserModel

    @GET("/shops/")
    suspend fun getShop(@Path("pk") pk: Long): ShopModel

    @GET("/shops")
    suspend fun getAllShops(): List<ShopModel>

    @GET("/shops/nearby")
    suspend fun getNearbyShops(): List<ShopModel>


}