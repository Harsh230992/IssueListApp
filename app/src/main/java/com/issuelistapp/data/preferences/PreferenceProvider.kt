package com.issuelistapp.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val KEY_SAVED_AT = "key_saved_at"

class PreferenceProvider(
    context: Context
) {

    private val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)


    fun Write(Key:String,value: String) {
        preference.edit().putString(
            Key,
            value
        ).apply()
    }

    fun Read(Key:String): String? {
        return preference.getString(Key, null)
    }

    fun Write(Key:String,value: Int) {
        preference.edit().putInt(
            Key,
            value
        ).apply()
    }

    fun ReadInt(Key:String): Int? {
        return preference.getInt(Key, 0)
    }

}