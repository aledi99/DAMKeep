package com.salesianostriana.keepdam.commons

import android.content.Context
import android.content.SharedPreferences
import com.salesianostriana.keepdam.di.MyApp

class SharedPreferencesManager {

    fun getSharedPreferences(): SharedPreferences {
        return  MyApp.instance?.getSharedPreferences(
            Constants.SHARED_PREFS_FILE, Context.MODE_PRIVATE
        )
    }

    fun setStringValue(label: String, value: String) {
        var edit: SharedPreferences.Editor = getSharedPreferences().edit()
        edit.putString(label, value)
        edit.commit()
    }

    fun removeStringValue(label: String) {
        var edit: SharedPreferences.Editor = getSharedPreferences().edit()
        edit.remove(label)
        edit.commit()

    }
}