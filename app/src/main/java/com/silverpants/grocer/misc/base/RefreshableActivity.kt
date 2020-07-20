package com.silverpants.grocer.misc.base

import androidx.appcompat.app.AppCompatActivity

abstract class RefreshableActivity : AppCompatActivity() {
    abstract fun updateScreen()
}