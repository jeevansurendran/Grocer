package com.silverpants.grocer.domain

import com.silverpants.grocer.hardware.network.Result
import timber.log.Timber

abstract class UseCase<in P, R> {

    operator fun invoke(parameters: P): Result<R> {
        return try {
            execute(parameters).let {
                Result.Success(it)
            }
        } catch (e: Exception) {
            Timber.d(e)
            Result.Error(e)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): R
}