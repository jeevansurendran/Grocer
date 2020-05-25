package com.silverpants.grocer.network.coflow

/**
 * the [Result] class is responsible for containing the data fetched from a source and
 * handle different scenarios regarding a data fetch.
 *
 * @param T the type of data present in the Resource object
 * @author @jeevansurendran
 * @since 1.0
 */
sealed class Result<T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Result<T>(
        Status.SUCCESS,
        data
    ) //need not be null and must be loaded from database but that will be taken care of later

    class Loading<T>(data: T? = null) : Result<T>(Status.LOADING, data)
    class Error<T>(message: String, data: T? = null) : Result<T>(Status.ERROR, data, message)
    class InvalidRequest<T>(message: String, data: T? = null) :
        Result<T>(Status.INVALID_REQUEST, data, message)

    companion object {
        @JvmStatic
        fun <T> cloneResource(data: T): Result<T> =
            Success(data)
    }

}
