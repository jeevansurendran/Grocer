package com.silverpants.grocer.data.auth

import com.silverpants.grocer.data.auth.models.TokenModel
import com.silverpants.grocer.data.auth.sources.AuthRemoteDataSource
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authRemoteDataSource:AuthRemoteDataSource){

    suspend fun postGuestRegister(idToken: String): TokenModel {
        return authRemoteDataSource.postGuestRegister(idToken)
    }

    suspend fun postGuestLogin(idToken: String): TokenModel {
        return authRemoteDataSource.postGuestLogin(idToken)
    }

    suspend fun postRefreshToken(refreshToken: String): TokenModel {
        return authRemoteDataSource.postRefreshToken(refreshToken)
    }

    suspend fun postLogout(refreshToken: String) {
        authRemoteDataSource.postLogout(refreshToken)
    }

}