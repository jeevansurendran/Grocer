package com.silverpants.grocer.view.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.silverpants.grocer.network.legacy.Resource
import com.silverpants.grocer.view.home.HomeRepository

class UserDetailsViewModel : ViewModel() {

    private val repository = HomeRepository

    private val liveData = liveData {
        emit(Resource.Loading(null))
        try {
            val response = repository.getUserDetails()
            emit(Resource.Success(response))
        } catch (e: Throwable) {
        }

    }

    val userDetails = liveData {
        emit(Resource.Loading(null))
        try {
            val response = repository.getUserDetails()
            emit(Resource.Success(response))
        } catch (e: Throwable) {
        }

    }

}