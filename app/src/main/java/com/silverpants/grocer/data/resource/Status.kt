package com.silverpants.grocer.data.resource

/**
 * The [Status] enum specifies the differenct cases a [Resource] can be present
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