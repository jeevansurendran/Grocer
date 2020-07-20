package com.silverpants.grocer.network

/**
 * The [Status] enum specifies the differenct cases a [Result] can be present
 *
 * @author @jeevansurendran
 * @since 1.0
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    INVALID_REQUEST;
}