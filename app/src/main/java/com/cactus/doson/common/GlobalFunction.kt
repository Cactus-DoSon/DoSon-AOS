package com.cactus.doson.common.util

import android.content.Context
import android.util.Log
import android.widget.Toast

internal fun printLog(tag: String, message: String) {
    Log.d(tag, message)
}
internal fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}