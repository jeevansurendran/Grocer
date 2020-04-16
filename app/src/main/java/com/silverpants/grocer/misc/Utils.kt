package com.silverpants.grocer.misc

import android.os.Build
import android.text.Html

object Utils {
    fun fromHtml(html: String?): CharSequence? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(html, 0) else Html.fromHtml(html)
    }
}