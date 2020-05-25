package com.silverpants.grocer.network.legacy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.Request
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type


/**
 * class for live data returned from retrofit call
 */
class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, Call<LiveData<ApiResponse<R>>>> {
    override fun adapt(call: Call<R>): Call<LiveData<ApiResponse<R>>> {
        return ApiResponseCall(call)
    }

    override fun responseType() = responseType
}

abstract class CallDelegate<TIn, TOut>(
    val proxy: Call<TIn>
) : Call<TOut> {
    override fun execute(): Response<TOut> = throw NotImplementedError()
    final override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)
    final override fun clone(): Call<TOut> = cloneImpl()

    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled

    abstract fun enqueueImpl(callback: Callback<TOut>)
    abstract fun cloneImpl(): Call<TOut>
}

class ApiResponseCall<R>(proxy: Call<R>) : CallDelegate<R, LiveData<ApiResponse<R>>>(proxy) {

    override fun enqueueImpl(callback: Callback<LiveData<ApiResponse<R>>>) =
        proxy.enqueue(object : Callback<R> {
            val liveData = MutableLiveData<ApiResponse<R>>()
            override fun onFailure(call: Call<R>, t: Throwable) {
                liveData.postValue(ApiResponse.create(t, call))
                callback.onResponse(this@ApiResponseCall, Response.success(liveData))
            }

            override fun onResponse(call: Call<R>, response: Response<R>) {
                liveData.postValue(ApiResponse.create(response, call))
                callback.onResponse(this@ApiResponseCall, Response.success(liveData))
            }

        })

    override fun cloneImpl() =
        ApiResponseCall(proxy.clone())
}