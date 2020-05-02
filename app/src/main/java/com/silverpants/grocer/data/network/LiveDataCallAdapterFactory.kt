package com.silverpants.grocer.data.network

import androidx.lifecycle.LiveData
import com.google.android.gms.common.api.Api
import com.silverpants.grocer.data.resource.ApiResponse
import retrofit2.CallAdapter
import retrofit2.CallAdapter.Factory
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Live data call adapter for retrofit
 */
class LiveDataCallAdapterFactory : Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val liveType = getParameterUpperBound(0, returnType as ParameterizedType)
        if (getRawType(liveType) != LiveData::class.java) {
            return null
        }
        val observableType = getParameterUpperBound(0, liveType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if (rawObservableType != ApiResponse::class.java) {
            throw IllegalStateException("must be an ApiResponse")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        val bodyType = getParameterUpperBound(0, observableType)

        return LiveDataCallAdapter<Any>(bodyType)
    }

}