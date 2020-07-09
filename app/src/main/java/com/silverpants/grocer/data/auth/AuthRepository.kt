package com.silverpants.grocer.data.auth

import com.silverpants.grocer.data.auth.Model.TokenModel
import com.silverpants.grocer.data.auth.sources.AuthRemoteDataSource

object AuthRepository {
    private val authRemoteDataSource = AuthRemoteDataSource

    suspend fun postGuestRegister(idToken: String): TokenModel {
        return authRemoteDataSource.postGuestRegister(idToken)
    }

    suspend fun postGuestLogin(idToken: String): TokenModel {
        return authRemoteDataSource.postGuestRegister(idToken)
    }

    suspend fun postRefreshToken(refreshToken: String): TokenModel {
        return authRemoteDataSource.postGuestRegister(refreshToken)
    }

    suspend fun postLogout(refreshToken: String) {
        authRemoteDataSource.postLogout(refreshToken)
    }

}