package com.pedraza.sebastian.search_domain.usecases.suggestions

import android.util.Log
import com.google.gson.Gson
import com.pedraza.sebastian.android_helpers.constants.PreferencesConstants
import com.pedraza.sebastian.android_helpers.preferences.StoreHelper
import com.pedraza.sebastian.core.BuildConfig
import com.pedraza.sebastian.core.utils.Constants.DEFAULT_ERROR_LOG_MESSAGE
import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.core.utils.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSearchSuggestionsUseCaseImpl(
    private val storeHelper: StoreHelper,
    private val gson: Gson
) : GetSearchSuggestionsUseCase {
    override fun invoke(): Result<Flow<List<String>>> {

        return try {
            val suggestions =
                storeHelper.getStringValue(PreferencesConstants.SUGGESTIONS_KEY).map {
                    gson.fromJson(it, Array<String>::class.java).toList()
                }
            Result.Success(suggestions)
        } catch (e: java.lang.Exception) {
            if (BuildConfig.DEBUG) Log.e(TAG, "$DEFAULT_ERROR_LOG_MESSAGE $e")
            Result.Error(UiText.DynamicString(e.message.orEmpty()))
        }
    }

    companion object {
        const val TAG = "GetSearchSuggestionsUseCase"
    }
}
