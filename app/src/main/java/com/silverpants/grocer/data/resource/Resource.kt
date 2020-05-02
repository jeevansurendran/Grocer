package com.silverpants.grocer.data.resource

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

/**
 * the [Resource] class is responsible for containing the data fetched from a source and
 * handle different scenarios regarding a data fetch.
 *
 * @param T the type of data present in the Resource object
 * @author @jeevansurendran
 * @since 1.0
 */
sealed class Resource<T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Resource<T>(
        Status.SUCCESS,
        data
    ) //need not be null and must be loaded from database but that will be taken care of later

    class Loading<T>(data: T? = null) : Resource<T>(Status.LOADING, data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(Status.ERROR, data, message)
    class InvalidRequest<T>(message: String, data: T? = null) :
        Resource<T>(Status.INVALID_REQUEST, data, message)

    companion object {
        fun <T> createResourceFromResponse(apiResponseLiveData: LiveData<ApiResponse<T>>): LiveData<Resource<T>> =
            Transformations.map(apiResponseLiveData) { response ->
                var resource: Resource<T> = Loading(null)
                when (response) {
                    is ApiSuccessResponse -> {
                        resource = try {
                            Success(response.body)
                        } catch (e: ClassCastException) {
                            Error("Invalid ResultType")
                        }
                    }
                    is ApiEmptyResponse -> {
                        resource = Success(null)
                    }
                    is ApiErrorResponse -> {
                        resource = Error(response.errorMessage)
                    }
                    is ApiInvalidRequestResponse -> {
                        resource = InvalidRequest(response.errorMessage)
                    }
                }
                resource
            }

        @JvmStatic
        fun <T> cloneResource(data: T): Resource<T> = Success(data)
    }

}
