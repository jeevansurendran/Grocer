package com.silverpants.grocer.misc

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

fun Fragment.toast(message:String) {
    Toast.makeText(requireContext(), message,Toast.LENGTH_SHORT).show()
}
fun Activity.toast(message:String) {
    Toast.makeText(applicationContext, message,Toast.LENGTH_SHORT).show()
}

// region Firebase
@ExperimentalCoroutinesApi
suspend fun <T> Task<T>.suspendAndWait(): T =
    suspendCancellableCoroutine { continuation ->
        addOnSuccessListener { result ->
            continuation.resume(result)
        }
        addOnFailureListener { exception ->
            continuation.resumeWithException(exception)
        }
        addOnCanceledListener {
            continuation.resumeWithException(Exception("Firebase Task was cancelled ?"))
        }
    }
// endregion