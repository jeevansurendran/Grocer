package com.silverpants.grocer

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.silverpants.grocer.network.WebApiClient
import timber.log.Timber
import timber.log.Timber.DebugTree


class GrocerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}