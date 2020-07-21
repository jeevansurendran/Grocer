package com.silverpants.grocer.misc

/**
 * A [Constants] object is a containing all the constants for this project
 *
 * @author @jeevansurendran
 * @since 1.0
 */
object Constants {

    //Networking
    private const val API_PROTOCOL = "http://"
    private const val API_HOST = "192.168.1.7"
    private const val API_PORT = "5000"

    const val baseUrl = "$API_PROTOCOL$API_HOST:$API_PORT/"
    const val AUTH_OTP_DEFAULT_TIMEOUT = 60 * 1.5.toLong()

    const val DEFAULT_REFRESH_RATE = 5000L
}