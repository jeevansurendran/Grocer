package com.silverpants.grocer.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.silverpants.grocer.MainActivity
import com.silverpants.grocer.R
import com.silverpants.grocer.auth.activities.AuthActivity

class OpenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open)
    }

    override fun onStart() {
        super.onStart()
        Thread.sleep(2000)
        val user = FirebaseAuth.getInstance().currentUser
        val intent = if(user == null) {
            Intent(this, AuthActivity::class.java)
        } else Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
