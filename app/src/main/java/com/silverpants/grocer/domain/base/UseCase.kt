package com.silverpants.grocer.domain.base

import com.silverpants.grocer.network.coflow.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * the [UseCase] executes the business logic for a particular use case.
 *
 * @author @jeevansurendran
 * @since 1.0
 */
abstract class UseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameter: P): R

    suspend operator fun invoke(parameter: P): Result<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameter).let {
                    Result.Success<R>(it)
                }
            }
        } catch (e: Exception) {
            Timber.d(e)
            Result.Error(e)
        }
    }
}