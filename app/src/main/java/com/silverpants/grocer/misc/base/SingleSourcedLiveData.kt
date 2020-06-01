package com.silverpants.grocer.misc.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer


/**
 * this class keeps a single source to observe from and removes previous source when new source is added and
 * hopefully wishes that GC removes the previous one.
 *
 * @author @jeevansurendran
 * @since 1.0
 */
class SingleSourcedLiveData<T> : MediatorLiveData<T>() {
    private var previousLiveData: LiveData<*>? = null

    override fun <S : Any?> addSource(source: LiveData<S>, onChanged: Observer<in S>) {
        previousLiveData?.let {
            removeSource(it)
        }
        super.addSource(source, onChanged)
        previousLiveData = source
    }

}