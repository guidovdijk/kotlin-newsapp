package nl.vandijk.guido.util

import android.content.Context
import android.content.SharedPreferences
import nl.vandijk.guido.R


object SessionManager {
    private lateinit var sharedPreferences: SharedPreferences
    private const val AUTH_TOKEN = "authToken"
    private const val EMAIL = "email"
    private const val PASSWORD = "password"
    private const val LOGGED_IN = "isLoggedIn"

    fun init(context: Context) {
        sharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    }

    fun getAuthToken(): String {
        return sharedPreferences.getString(AUTH_TOKEN, "") ?: ""
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(LOGGED_IN, false)
    }

    fun removeData() {
        sharedPreferences.edit().apply {
            putString(EMAIL, "")
            putString(PASSWORD, "")
            putString(AUTH_TOKEN, "")
            putBoolean(LOGGED_IN, false)
        }.apply()
    }

    fun saveData(email: String, password: String, authToken: String) {
        sharedPreferences.edit().apply {
            putString(EMAIL, email)
            putString(PASSWORD, password)
            putString(AUTH_TOKEN, authToken)
            putBoolean(LOGGED_IN, true)
        }.apply()
    }
}