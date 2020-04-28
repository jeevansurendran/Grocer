package com.silverpants.grocer.home.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val navController: NavController by lazy { findNavController(R.id.nav_host_home) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(toolbar)

        setupBottomNavigationMenu()


    }

    private fun setupBottomNavigationMenu() {
        val bottomNavigationView = binding.bnHome
        bottomNavigationView.setupWithNavController(navController)
    }
}
