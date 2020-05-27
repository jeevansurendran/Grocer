package com.silverpants.grocer.network.coflow

import java.lang.Exception

/**
 * the [Result] class is responsible for containing the data fetched from a source and
 * handle different scenarios regarding a data fetch.
 *
 * @param T the type of data present in the Resource object
 * @author @jeevansurendran
 * @since 1.0
 */
sealed class Result<out T>(
    val status: Status,
    val data: T? = null,
    val exception: Exception? = null
) {
    class Success<T>(data: T?) : Result<T>(
        Status.SUCCESS,
        data
    )

    class Loading<T>(data: T? = null) : Result<T>(Status.LOADING, data)
    class Error(exception: Exception) : Result<Nothing>(Status.ERROR,null, exception)
    class InvalidRequest<T>(exception: Exception, data: T? = null) :
        Result<T>(Status.INVALID_REQUEST, data, exception)
}
