package com.example.structure.util

import android.content.Context
import android.content.SharedPreferences
import com.example.structure.core.MyApplication

object Pref {

    private var sharedPreferences: SharedPreferences? = null

    val PREF_FILE: String = "VOLTPOINT_PREF"
    private fun openPreference() {

        sharedPreferences = MyApplication.instance.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)

    }

    /*Fore String Value Store*/

    fun setStringValue(key: String, value: String) {
        openPreference()
        val prefsPrivateEditor: SharedPreferences.Editor? = sharedPreferences!!.edit()
        prefsPrivateEditor!!.putString(key, value)
        prefsPrivateEditor.apply()
        sharedPreferences = null
    }

    fun getStringValue(key: String, defaultValue: String): String {
        openPreference()
        val result = sharedPreferences!!.getString(key, defaultValue).toString()
        sharedPreferences = null
        return result
    }


    /*For Integer Value*/
    fun setIntValue(key: String, value: Int) {
        openPreference()
        val prefsPrivateEditor: SharedPreferences.Editor? = sharedPreferences!!.edit()
        prefsPrivateEditor!!.putInt(key, value)
        prefsPrivateEditor.apply()
        sharedPreferences = null
    }

    fun getIntValue(key: String, defaultValue: Int): Int {
        openPreference()
        val result = sharedPreferences!!.getInt(key, defaultValue)
        sharedPreferences = null
        return result
    }


    /*For Float Value*/
    fun setFloatValue(key: String, value: Float) {
        openPreference()
        val prefsPrivateEditor: SharedPreferences.Editor? = sharedPreferences!!.edit()
        prefsPrivateEditor!!.putFloat(key, value)
        prefsPrivateEditor.apply()
        sharedPreferences = null
    }

    fun getFloatValue(key: String, defaultValue: Float): Float {
        openPreference()
        val result = sharedPreferences!!.getFloat(key, defaultValue)
        sharedPreferences = null
        return result
    }

    /*For boolean Value Store*/

    fun setBooleanValue(key: String, value: Boolean) {
        openPreference()
        val prefsPrivateEditor: SharedPreferences.Editor? = sharedPreferences!!.edit()
        prefsPrivateEditor!!.putBoolean(key, value)
        prefsPrivateEditor.apply()
        sharedPreferences = null
    }

    fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        openPreference()
        val result = sharedPreferences!!.getBoolean(key, defaultValue)
        sharedPreferences = null
        return result
    }


    /*For Remove variable from pref*/
    fun remove(key: String) {
        openPreference()
        val prefsPrivateEditor = sharedPreferences!!.edit()
        prefsPrivateEditor.remove(key)
        prefsPrivateEditor.apply()
        sharedPreferences = null
    }

    /*For Remove variable from pref*/
    fun clearAllPref() {
        remove(PREF_IS_LOGIN)

    }

    const val PREF_IS_LOGIN: String = "PREF_IS_LOGIN"
    const val PREF_AUTH_TOKEN: String = "PREF_AUTH_TOKEN"
    const val PREF_LANGUAGE: String = "PREF_LANGUAGE"



}