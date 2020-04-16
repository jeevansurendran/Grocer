package com.silverpants.grocer.data.network

import com.fasterxml.jackson.databind.node.ObjectNode
import com.silverpants.grocer.auth.models.UserModel
import retrofit2.Call
import retrofit2.http.POST

interface WebApiService {
    @POST("users/resister")
    fun register(userCredentials: ObjectNode) : Call<UserModel>
}