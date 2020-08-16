package com.silverpants.grocer.view.home.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.silverpants.grocer.hardware.network.Result
import com.silverpants.grocer.view.home.HomeRepository

class UserDetailsViewModel @ViewModelInject constructor(repository: HomeRepository) : ViewModel() {

    val userDetailsLiveData = liveData {
        emit(Result.Loading())
        emit(Result.Success(repository.getUserDetails()))
    }


}