package com.silverpants.grocer.home.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.ActivityHomeBinding
import com.silverpants.grocer.misc.base.BaseActivity

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val navController: NavController by lazy { findNavController(R.id.nav_host_home) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigationMenu()
    }

    private fun setupBottomNavigationMenu() {
        val bottomNavigationView = binding.bnHome
        bottomNavigationView.setupWithNavController(navController)
    }
}
