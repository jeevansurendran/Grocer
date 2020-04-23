package com.silverpants.grocer.data.resource

import android.util.Log
import retrofit2.Call
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_OK

/**
 * The [ApiResponse] class is a common class used for all web api responses
 *
 * @param T the type of response object
 */
sealed class ApiResponse<T>() {

    init {
        Log.d(TAG, "Call url\t: $url")
        Log.d(TAG, "Call status\t: $code")
    }

    companion object {
        var url: String? = null
        var code: Int? = null
        val TAG = ApiResponse::class.simpleName

        fun <T> create(error: Throwable, call: Call<T>): ApiResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>, call: Call<T>): ApiResponse<T> {
            url = call.request().url().toString()
            code = response.code()
            return if (response.isSuccessful) {
                val body = response.body()

                if (body == null || response.code() == HTTP_OK) {
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

class ApiEmptyResponse<T> : ApiResponse<T>()
data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()

data class ApiInvalidRequestResponse<T>(val errorMessage: String) : ApiResponse<T>()