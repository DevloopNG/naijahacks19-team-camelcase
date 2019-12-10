package cc.properton.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class PrefManager(private val ctx: Context) {
    var sharedPref: SharedPreferences
    var sharedPrefEditor: SharedPreferences.Editor
    var gson: Gson

    init {
        sharedPref = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPrefEditor = sharedPref.edit()
        gson = Gson()
    }

    var isFirstLaunch: Boolean
        get() = sharedPref.getBoolean(IS_FIRST_LAUNCH, true)
        set(value) {
            sharedPrefEditor.putBoolean(IS_FIRST_LAUNCH, value).apply()
        }

    companion object {
        const val PREF_NAME = "main_prefs"
        const val IS_FIRST_LAUNCH = "is_first_launch"
    }
}