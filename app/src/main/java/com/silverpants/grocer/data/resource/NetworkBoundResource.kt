package com.silverpants.grocer.data.resource

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * A generic class [NetworkBoundResource] that can provide a resource backed by both an offline database and the network.
 * Presently the data is only taken from network and no data is saved in the local database.
 * Planning to save the database
 *
 * [ResultType] is the type of resource data obtained
 * [RequestType] is the type for the api response
 *
 * @param ResultType
 * @param RequestType
 * @author @jeevansurendran
 * @since 1.0
 */
abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        @Suppress("LeakingThis")
        val apiResponse = createCall()
        setValue(Resource.Loading())

        result.addSource(apiResponse) { response ->
            when (response) {
                is ApiSuccessResponse -> {
                    try {
                        setValue(Resource.Success(resultData(response.body)))
                    } catch (e: ClassCastException) {
                        setValue(Resource.Error("Invalid ResultType"))
                    }
                }
                is ApiEmptyResponse -> {
                    setValue(Resource.Success(data = null))
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    setValue(Resource.Error(response.errorMessage))
                }
                is ApiInvalidRequestResponse -> {
                    onFetchFailed()
                    setValue(Resource.InvalidRequest(response.errorMessage))
                }
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    private fun resultData(data: RequestType): ResultType = data as ResultType

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    // Called to save the result of the API response into the database
    @WorkerThread
    protected open fun saveCallResult(item: RequestType) {
    }

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected open fun shouldFetch(data: ResultType?): Boolean = true //no data saving for now

    // Called to get the cached data from the database.
    @MainThread
    protected open fun loadFromDb(): LiveData<ResultType>? = null

    // Called to create the API call.
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    // Called when the fetch fails. The child class may want to reset components
    protected open fun onFetchFailed() {}

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.   
    val asLiveData: LiveData<Resource<ResultType>> get() = result


}