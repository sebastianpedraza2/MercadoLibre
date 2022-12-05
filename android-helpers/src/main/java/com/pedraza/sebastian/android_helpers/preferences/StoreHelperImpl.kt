package com.pedraza.sebastian.android_helpers.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreHelperImpl(private val context: Context) : StoreHelper {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "meli")

    override suspend fun saveBooleanValue(key: String, value: Boolean) {
        context.dataStore.edit { settings ->
            settings[booleanPreferencesKey(key)] = value
        }
    }

    override fun getBooleanValue(key: String): Flow<Boolean> =
        context.dataStore.data
            .map { preferences ->
                preferences[booleanPreferencesKey(key)] ?: false
            }

    override suspend fun saveStringValue(key: String, value: String) {
        context.dataStore.edit { settings ->
            settings[stringPreferencesKey(key)] = value
        }
    }

    override fun getStringValue(key: String): Flow<String> =
        context.dataStore.data
            .map { preferences ->
                preferences[stringPreferencesKey(key)] ?: "[]"
            }
}
