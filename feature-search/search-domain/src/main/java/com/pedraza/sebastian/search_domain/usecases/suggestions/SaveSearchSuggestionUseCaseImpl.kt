package com.pedraza.sebastian.search_domain.usecases.suggestions

import android.util.Log
import com.google.gson.Gson
import com.pedraza.sebastian.android_helpers.constants.PreferencesConstants
import com.pedraza.sebastian.android_helpers.preferences.StoreHelper
import com.pedraza.sebastian.core.BuildConfig
import com.pedraza.sebastian.core.utils.Constants.DEFAULT_ERROR_LOG_MESSAGE
import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.core.utils.UiText

class SaveSearchSuggestionUseCaseImpl(
    private val storeHelper: StoreHelper,
    private val gson: Gson
) : SaveSearchSuggestionUseCase {
    override suspend fun invoke(previousList: List<String>, suggestion: String): Result<Unit> {
        return try {
            if (suggestion in previousList || suggestion.isEmpty()) return Result.Success(Unit)
            val updatedList = if (previousList.size >= MAX_LIST_SIZE) {
                addSuggestionToPreviousList(
                    previousList.drop(LIST_INDEX),
                    suggestion
                )
            } else {
                addSuggestionToPreviousList(
                    previousList,
                    suggestion
                )
            }
            val updatedJson = gson.toJson(updatedList)
            storeHelper.saveStringValue(
                PreferencesConstants.SUGGESTIONS_KEY,
                updatedJson
            )
            Result.Success(Unit)
        } catch (e: java.lang.Exception) {
            if (BuildConfig.DEBUG) Log.e(TAG, "$DEFAULT_ERROR_LOG_MESSAGE $e")
            Result.Error(UiText.DynamicString(e.message.orEmpty()))
        }
    }

    private fun addSuggestionToPreviousList(
        previousList: List<String>,
        suggestion: String
    ): List<String> = previousList + suggestion

    companion object {
        const val TAG = "SaveSearchSuggestionUseCase"
        const val LIST_INDEX = 1
        const val MAX_LIST_SIZE = 10
    }
}
