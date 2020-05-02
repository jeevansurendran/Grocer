package com.silverpants.grocer.data.network2

import androidx.lifecycle.MediatorLiveData
import retrofit2.Response

/**
 * This class acts as the perfect result that has been provided by api Response
 */

abstract class RetrofitLiveData<T> constructor(): MediatorLiveData<T>() {

    abstract suspend fun createRetrofitLiveData();
}




