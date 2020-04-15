package com.silverpants.grocer.data.resource

import retrofit2.Response

/**
 * The [ApiResponse] class is a common class used for all web api responses
 *
 * @param T the type of response object
 */
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>) : ApiResponse<T> {
            return if(response.isSuccessful) {
                val body = response.body()
                if(body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(
                        body = body
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(errorMsg ?: "unknown error")
            }
        }
    }
}
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) :ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()