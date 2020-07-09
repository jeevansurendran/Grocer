package com.silverpants.grocer.misc

import com.silverpants.grocer.R
import timber.log.Timber

/**
 * Converts [ErrorCodes] from firebase to translatable strings.
 */
object FirebaseAuthErrorCodeConverter {

    fun convert(code: Int): Int {
        return when (code) {
            ErrorCodes.NO_NETWORK -> {
                Timber.e("FirebaseAuth error: no_network")
                R.string.firebase_auth_no_network_connection
            }
            ErrorCodes.DEVELOPER_ERROR -> {
                Timber.e("FirebaseAuth error: developer_error")
                R.string.firebase_auth_unknown_error
            }
            ErrorCodes.PLAY_SERVICES_UPDATE_CANCELLED -> {
                Timber.e("FirebaseAuth error: play_services_update_cancelled")
                R.string.firebase_auth_unknown_error
            }
            ErrorCodes.PROVIDER_ERROR -> {
                Timber.e("FirebaseAuth error: provider_error")
                R.string.firebase_auth_unknown_error
            }
            else -> {
                Timber.e("FirebaseAuth error: unknown_error")
                R.string.firebase_auth_unknown_error
            }
        }
    }
}