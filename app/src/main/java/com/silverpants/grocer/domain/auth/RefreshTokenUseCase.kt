package com.silverpants.grocer.domain.auth

import com.silverpants.grocer.data.auth.AuthRepository
import com.silverpants.grocer.data.auth.Model.TokenModel
import com.silverpants.grocer.domain.UseCase
import kotlinx.coroutines.Dispatchers

/**
 * Not exactly a business logic but keeps code consistent
 */
class RefreshTokenUseCase :UseCase<String, TokenModel>(Dispatchers.Default) {
    private val authRepository: AuthRepository = AuthRepository

    override suspend fun execute(refreshToken: String): TokenModel {
        return authRepository.postRefreshToken(refreshToken)
    }

}