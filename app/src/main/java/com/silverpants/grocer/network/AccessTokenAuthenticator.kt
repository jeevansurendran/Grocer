package com.silverpants.grocer.network

import com.silverpants.grocer.data.auth.Model.TokenModel
import com.silverpants.grocer.domain.auth.RefreshTokenUseCase
import com.silverpants.grocer.network.coflow.Result
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber

/**
 * Authenticator that attempts to refresh the client's access token.
 * In the event that a refresh fails and a new token can't be issued an error
 * is delivered to the caller. This authenticator blocks all requests while a token
 * refresh is being performed. In-flight requests that fail with a 401 are
 * automatically retried.
 *
 * this is not gonna remain after i integrate dagger
 */
class AccessTokenAuthenticator(
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // We need to have a token in order to refresh it.
        val token = NetworkModule.token
        Timber.d("hello");
        if (token == null || response.request().header("Authorization") == null) {
            return null;
        }

        val newToken = runBlocking {
            val refreshToken = RefreshTokenUseCase()
            when(val refreshTokenResult = refreshToken(token.refreshToken)) {
                is Result.Success -> {
                    refreshTokenResult .data
                }
                else -> {
                    return@runBlocking null
                }
            }
        } ?: return null
        NetworkModule.token = newToken

        // Retry the request with the new token.
        return response.request()
            .newBuilder()
            .removeHeader("Authorization")
            .addHeader("Authorization", "Bearer ${newToken.accessToken}")
            .build()
    }
}