package com.silverpants.grocer.data.resource

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
    class InvalidRequest<T>(message: String, data: T? = null) : Resource<T>(Status.INVALID_REQUEST, data, message)

}
