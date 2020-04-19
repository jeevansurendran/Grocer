package com.silverpants.grocer.misc

import android.os.Build
import android.text.Html
import java.util.*

object Utils {
    fun fromHtml(html: String?): CharSequence? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(
            html,
            0
        ) else Html.fromHtml(html)
    }

    fun formatTime(min: Long, sec: Long): String {
        return if (min == 0L) String.format(Locale.ENGLISH, "%02d seconds", sec)
        else String.format(Locale.ENGLISH, "%02d:%02d minutes", min, sec)
    }
}