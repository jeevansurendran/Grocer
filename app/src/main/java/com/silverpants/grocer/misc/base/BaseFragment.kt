package com.silverpants.grocer.misc.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes res: Int) : Fragment(res)