package com.pedraza.sebastian.core.utils

import android.content.Context
import androidx.annotation.StringRes


sealed class UiText {
    data class DynamicString(val text: String) : UiText()
    data class ResourcesString(@StringRes val resId: Int) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> text
            is ResourcesString -> context.getString(resId)
        }
    }
}