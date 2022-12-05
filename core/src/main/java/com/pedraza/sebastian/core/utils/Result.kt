package com.pedraza.sebastian.core.utils

import android.util.Log
import androidx.annotation.StringRes
import com.pedraza.sebastian.core.BuildConfig
import com.pedraza.sebastian.core.R
import retrofit2.Response

sealed class Result<T>(
    open val data: T? = null,
    open val message: UiText? = null
) {
    class Success<T>(override val data: T) : Result<T>(data)
    class Error<T>(override val message: UiText, data: T? = null) : Result<T>(data, message)

    companion object {
        const val TAG = "Result"
    }
}

/**
 * Returns a boolean indicating if a network response is valid or not
 * @param response: The network response wrapper
 */

fun <T> isResponseValid(response: Response<T>): Boolean {
    return response.body() != null && response.isSuccessful
}

/**
 * Checks if a network response was successful and returns the domain model in a Result wrapper
 * @param condition: Used to validate the network response
 * @param action: Action to parse the encapsulated response value into a domain value
 * @param errorMessage: Default error message from string res
 * @return [Result] wrapper
 */

inline fun <T, E> Response<T>.toResult(
    condition: Boolean = isResponseValid(this),
    action: (T) -> Result.Success<E>,
    @StringRes errorMessage: Int = R.string.meli_default_error
): Result<E> {
    return if (condition) {
        action(this.body()!!)
    } else {
        Result.Error(UiText.ResourcesString(errorMessage))
    }
}

/**
 * Checks if a network response was successful and returns the domain model in a Result wrapper in a safely manner
 * @param dataRequest: Async operation to get data
 * @param action: Action to parse the encapsulated response value into a domain value
 * @return [Result] wrapper
 */

suspend fun <T, E> resolveResponse(
    dataRequest: suspend () -> Response<T>,
    action: (T) -> Result.Success<E>
): Result<E> {
    return try {
        val mercadoLibreResponse = dataRequest()
        mercadoLibreResponse.toResult(action = action)
    } catch (error: Exception) {
        // Maybe send some report to crashlytics
        if (BuildConfig.DEBUG) Log.e(Result.TAG, "Message: $error"
        )
        Result.Error(UiText.DynamicString(error.message.toString()))
    }
}

/**
 * Created by Sebastian Pedraza
 */
