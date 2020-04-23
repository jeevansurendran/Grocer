package com.silverpants.grocer.data.resource

import retrofit2.Call
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_NO_CONTENT

/**
 * The [ApiResponse] class is a common class used for all web api responses
 *
 * @param T the type of response object
 * @author @jeevansurendran
 * @since 1.0
 */
sealed class ApiResponse<T>() {
    companion object {
        var url: String? = null
        var code: Int? = null
        val TAG = ApiResponse::class.simpleName

        fun <T> create(error: Throwable, call: Call<T>): ApiResponse<T> {
            url = call.request().url().toString()
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>, call: Call<T>): ApiResponse<T> {
            url = call.request().url().toString()
            code = response.code()
            return if (response.isSuccessful) {
                val body = response.body()

                if (body == null || response.code() == HTTP_NO_CONTENT) {
                    ApiEmptyResponse()

                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) response.message() else msg
                if (code == HTTP_BAD_REQUEST)
                    ApiInvalidRequestResponse(errorMsg ?: "unknown error")
                else
                    ApiErrorResponse(errorMsg ?: "unknown error")
            }
        }
    }
}

/**
 * [ApiEmptyResponse] is a class that is responsible to handle [HTTP_NO_CONTENT] or an empty response
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

/**
 * [ApiSuccessResponse] is a class that is responsible to handle a successful response
 */
data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

/**
 * [ApiErrorResponse] is a class that is used to handle failed request or an error response
 */
data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()

/**
 * [ApiInvalidRequestResponse] is a class that is responsible to handle [HTTP_BAD_REQUEST]
 */
data class ApiInvalidRequestResponse<T>(val errorMessage: String) : ApiResponse<T>()