package com.silverpants.grocer.misc.base

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.silverpants.grocer.hardware.network.Result

abstract class RefreshableFragment(@LayoutRes res: Int) : Fragment(res) {

    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    protected fun initRefreshScreen(@IdRes viewId: Int) = view?.let {
        swipeRefreshLayout = it.findViewById(viewId)
        swipeRefreshLayout?.setOnRefreshListener { this.updateScreen() }
    }

    open fun updateScreen() {
        (activity as? RefreshableActivity)?.updateScreen()
    }

    protected fun startRefreshing() {
        swipeRefreshLayout?.isRefreshing = true
    }

    protected fun stopRefreshing() {
        swipeRefreshLayout?.isRefreshing = false
    }
    fun handleLoadingRefresh(result: Result<*>) {
        when (result) {
            is Result.Loading-> {
                //do nothing
            }
            else -> stopRefreshing()
        }
    }

}