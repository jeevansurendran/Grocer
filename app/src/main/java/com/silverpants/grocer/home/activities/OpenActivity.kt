package com.silverpants.grocer.home.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.silverpants.grocer.MainActivity
import com.silverpants.grocer.R
import com.silverpants.grocer.data.network.WebApiClient

/**
 * The [OpenActivity] displays the logo and opens into the [MainActivity]
 *
 * @author @jeevansurendran
 * @since 1.0
 */
class OpenActivity : AppCompatActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open)
    }

    override fun onStart() {
        super.onStart()
        Thread.sleep(2000)
        val user = firebaseAuth.currentUser
        if (user == null) {
            createAnonymousUser()
        }
        user?.getIdToken(true)?.addOnCompleteListener {
            if (it.isSuccessful) {
                WebApiClient.idToken = it.result?.token
            }
        }
        finish()
    }

    private fun createAnonymousUser() {
        firebaseAuth.signInAnonymously()
    }
}
