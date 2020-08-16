package com.silverpants.grocer.hardware.network

import com.silverpants.grocer.data.Converters
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

/**
 * Authenticator that attempts to refresh the client's access token.
 * In the event that a refresh fails and a new token can't be issued an error
 * is delivered to the caller. This authenticator blocks all requests while a token
 * refresh is being performed. In-flight requests that fail with a 401 are
 * automatically retried.
 *
 * this is not gonna remain after i integrate dagger
 */
class AccessTokenAuthenticator @Inject constructor(private val serviceWrapper: Lazy<RamenApiService>) :
    Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // We need to have a token in order to refresh it.
        val token = NetworkModule.token
        if (token == null || response.request.header("Authorization") == null) {
            return null
        }

        val newToken = runBlocking {
            val data = Converters.objectMapper.createObjectNode()
            data.put("refreshToken", token.refreshToken)
            try {
                serviceWrapper.get().postRefreshToken(data)
            } catch (e: Exception) {
                null
            }
        } ?: return null
        NetworkModule.token = newToken

        // Retry the request with the new token.
        return response.request
            .newBuilder()
            .removeHeader("Authorization")
            .addHeader("Authorization", "Bearer ${newToken.accessToken}")
            .build()
    }
}