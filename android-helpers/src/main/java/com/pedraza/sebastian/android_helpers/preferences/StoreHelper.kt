package com.pedraza.sebastian.android_helpers.preferences

import kotlinx.coroutines.flow.Flow

/**
 * Provides common data storage functionality
 */
interface StoreHelper {
    suspend fun saveBooleanValue(key: String, value: Boolean)
    fun getBooleanValue(key: String): Flow<Boolean>
    suspend fun saveStringValue(key: String, value: String)
    fun getStringValue(key: String): Flow<String>
}
