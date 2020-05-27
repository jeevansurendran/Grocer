package com.silverpants.grocer.data.users

import com.silverpants.grocer.data.users.models.UserModel
import com.silverpants.grocer.data.users.sources.UserRemoteDataSource

object UserRepository {
    private val userRemoteDataSource = UserRemoteDataSource
    suspend fun guestRegister(idToken: String): UserModel {
        return userRemoteDataSource.guestRegister(idToken)
    }
}