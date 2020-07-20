package com.silverpants.grocer.domain.auth

import com.silverpants.grocer.data.auth.AuthRepository
import com.silverpants.grocer.data.auth.Model.TokenModel
import com.silverpants.grocer.di.DefaultDispatcher
import com.silverpants.grocer.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Not exactly a business logic but keeps code consistent
 */
class RefreshTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @DefaultDispatcher dispatcher: CoroutineDispatcher
) :
    UseCase<String, TokenModel>(dispatcher) {

    override suspend fun execute(parameters: String): TokenModel {
        return authRepository.postRefreshToken(parameters)
    }

}