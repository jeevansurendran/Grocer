package com.silverpants.grocer.hardware.network

import com.fasterxml.jackson.databind.node.ObjectNode
import com.silverpants.grocer.data.auth.models.TokenModel
import com.silverpants.grocer.data.shops.models.ShopModel
import com.silverpants.grocer.data.users.models.UserModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RamenApiService {
    /* Authentication */
    @POST("/auth/guests/register")
    suspend fun postGuestRegister(@Body userCredentials: ObjectNode): TokenModel

    @POST("/auth/guests/login")
    suspend fun postGuestLogin(@Body userCredentials: ObjectNode): TokenModel

    @POST("/auth/token")
    suspend fun postRefreshToken(@Body refreshToken: ObjectNode): TokenModel

    @POST("/auth/logout")
    suspend fun postLogout(@Body refreshToken: ObjectNode)

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