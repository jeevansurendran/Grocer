package com.silverpants.grocer.hardware.network

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
        Status.SUCCESS, data)
    class Loading : Result<Nothing>(
        Status.LOADING
    )
    class Error(exception: Exception) : Result<Nothing>(
        Status.ERROR, null, exception)

}
