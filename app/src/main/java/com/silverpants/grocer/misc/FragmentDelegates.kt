@file:Suppress("UNCHECKED_CAST")

package com.silverpants.grocer.misc

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Delegate that sets and disposes the fragment's listener by casting it to the fragment's activity.
 */
class ParentActivityDelegate<T>(fragment: Fragment) : BaseParentDelegate<T>(fragment) {
    override fun extractValue(fragment: Fragment): T? = fragment.activity as? T
}

/**
 * Delegate that sets and disposes the fragment's listener by casting it to the fragment's
 * parent fragment.
 */
class ParentFragmentDelegate<T>(fragment: Fragment) : BaseParentDelegate<T>(fragment) {
    override fun extractValue(fragment: Fragment): T? = fragment.parentFragment as? T
}

/**
 * Base delegate that sets and disposes the fragment's listener when the fragment is
 * created and destroyed.
 */
abstract class BaseParentDelegate<T>(private val fragment: Fragment) :
    ReadOnlyProperty<Fragment, T>, LifecycleObserver {

    private var value: T? = null

    init {
        fragment.lifecycle.addObserver(this)
    }

    /**
     * Extract [T] from the fragment. This is called when the fragment is attached to the parent.
     */
    abstract fun extractValue(fragment: Fragment): T?

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        value = extractValue(fragment)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        value = null
    }

    override operator fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (value == null) value = extractValue(thisRef)
        return value!!
    }
}

fun <T> Fragment.parentActivityDelegate(): BaseParentDelegate<T> = ParentActivityDelegate(this)

fun <T> Fragment.parentFragmentDelegate(): BaseParentDelegate<T> = ParentFragmentDelegate(this)
