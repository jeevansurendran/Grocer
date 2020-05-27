package com.silverpants.grocer.open.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.silverpants.grocer.R
import com.silverpants.grocer.home.activities.HomeActivity
import com.silverpants.grocer.misc.toast
import com.silverpants.grocer.network.WebApiClient
import com.silverpants.grocer.network.legacy.Resource
import com.silverpants.grocer.open.viewmodels.OpenViewModel

/**
 * The [OpenActivity] displays the logo and opens into the [HomeActivity]
 *
 * @author @jeevansurendran
 * @since 1.0
 */
class OpenActivity : AppCompatActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val viewModel: OpenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open)

        val intent = Intent(this, HomeActivity::class.java)

        firebaseAuth.addIdTokenListener { firebaseAuth: FirebaseAuth ->
            firebaseAuth.currentUser?.getIdToken(false)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    WebApiClient.idToken = it.result?.token!!
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.e(TAG, it.exception?.message!!)
                }
            }
        }

        viewModel.authResult.observe(this, Observer {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        toast("registered wow")
                    }
                }
            }
        })


        Thread.sleep(2000)
        val user = firebaseAuth.currentUser
        if (user == null) {
            firebaseAuth.signInAnonymously().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.user?.getIdToken(false)?.addOnCompleteListener {
                        viewModel.guestRegisterUser(it.result?.token!!)
                    }
                }
            }
        }
    }

    companion object {
        val TAG = OpenActivity::class.simpleName
    }

}
