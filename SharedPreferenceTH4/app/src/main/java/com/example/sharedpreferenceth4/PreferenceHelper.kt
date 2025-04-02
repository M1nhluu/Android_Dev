package com.example.sharedpreferenceth4

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceHelper(context: Context) {
    private val name = "user_prefs"
    private val userNameKey = "username"
    private val passwordKey = "password"

    private val sharedPref: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun saveUser(username: String, password: String) {
        sharedPref.edit().apply {
            putString(userNameKey, username)
            putString(passwordKey, password)
            commit()
        }
    }

    fun getUser(): Pair<String?, String?> {
        val username = sharedPref.getString(userNameKey, null)
        val password = sharedPref.getString(passwordKey, null)
        return Pair(username, password)
    }

    fun deleteUser() {
        sharedPref.edit { clear() }
    }
}