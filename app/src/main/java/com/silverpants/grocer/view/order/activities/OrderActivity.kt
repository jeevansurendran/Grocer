package com.silverpants.grocer.view.order.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.silverpants.grocer.databinding.ActivityOrderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}