package com.silverpants.grocer.view.order.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.silverpants.grocer.databinding.ActivityOrderBinding
import com.silverpants.grocer.view.order.viewmodels.ToolbarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityOrderBinding

    private val toolBarViewModel: ToolbarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setupObservers()
    }

    private fun setupObservers() {
        toolBarViewModel.toolbarTextLiveData.observe(this, Observer {
            it?.let {
                viewBinding.tvOrderToolbarText.text = it
            }
        })
        viewBinding.imOrderToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }
}