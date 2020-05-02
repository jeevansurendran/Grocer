package com.silverpants.grocer.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.silverpants.grocer.data.resource.Resource
import com.silverpants.grocer.home.HomeRepository

class UserDetailsViewModel : ViewModel() {

    private val repository = HomeRepository

    val userDetails = liveData {
        emit(Resource.Loading(null))
        val response = repository.getUserDetails()
        try {
            emit(Resource.Success(response.body()))
        } catch (e: Throwable) {
        }

    }

}