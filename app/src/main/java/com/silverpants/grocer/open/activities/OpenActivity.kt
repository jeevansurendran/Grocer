package com.silverpants.grocer.open.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.silverpants.grocer.R
import com.silverpants.grocer.home.activities.HomeActivity
import com.silverpants.grocer.misc.toast
import com.silverpants.grocer.network.coflow.Result
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

    private val idTokenListener = FirebaseAuth.IdTokenListener { firebaseAuth ->
        firebaseAuth.currentUser?.getIdToken(false)?.addOnCompleteListener {
            if (it.isSuccessful) {
                viewModel.setVerified(true)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open)
        setupObservers()
        val user = firebaseAuth.currentUser
        if (user == null) {
            firebaseAuth.signInAnonymously().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.user?.getIdToken(false)?.addOnCompleteListener {
                        viewModel.registerGuest(it.result?.token!!)
                    }
                }
            }
        }
        firebaseAuth.addIdTokenListener(idTokenListener)
    }

    private fun setupObservers() {

        viewModel.authResult.observe(this, Observer {
            it?.let {
                when (it) {
                    is Result.Success -> {
                        toast("registered wow")
                    }
                }
            }
        })
        viewModel.loginVerified.observe(this, Observer {
            if (it) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        firebaseAuth.removeIdTokenListener(idTokenListener)
    }


    companion object {
        val TAG = OpenActivity::class.simpleName
    }

}
