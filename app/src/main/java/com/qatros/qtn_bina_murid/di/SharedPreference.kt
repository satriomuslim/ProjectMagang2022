package com.qatros.qtn_bina_murid.di

import android.annotation.SuppressLint
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SharedPreference(val context: Context) {
    companion object {
        private const val PREF_NAME ="qatros.qtn_bina_murid.user_token"
        private const val USER_TOKEN = "user.token"
        private const val IS_LOGIN = "is.login"
        private const val USER_ID = "user.id"
        private const val USER_ROLE = "user.role"
        private const val USER_NAME = "user.name"
        private const val USER_TELP = "user.telp"
        private const val USER_ADDRESS = "user.address"
        private const val USER_EMAIL = "user.email"
    }
    @SuppressLint("NewApi")
    private val masterKeyAlias= MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    private val pref = EncryptedSharedPreferences.create(context,PREF_NAME,masterKeyAlias,EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

    var userToken : String
        get() = pref.getString(USER_TOKEN,"").toString()
        set(value) = pref.edit().putString(USER_TOKEN,value).apply()

    var isLogin : Boolean
        get() = pref.getBoolean(IS_LOGIN, false)
        set(value) = pref.edit().putBoolean(IS_LOGIN,value).apply()

    var userRole : Int
        get() = pref.getInt(USER_ROLE, 0)
        set(value) = pref.edit().putInt(USER_ROLE,value).apply()

    var userId : Int
        get() = pref.getInt(USER_ID, 0)
        set(value) = pref.edit().putInt(USER_ID,value).apply()

    var userName : String
        get() = pref.getString(USER_NAME,"").toString()
        set(value) = pref.edit().putString(USER_NAME,value).apply()

    var userEmail : String
        get() = pref.getString(USER_EMAIL,"").toString()
        set(value) = pref.edit().putString(USER_EMAIL,value).apply()

    var userTelp : String
        get() = pref.getString(USER_TELP,"").toString()
        set(value) = pref.edit().putString(USER_TELP,value).apply()

    var userAddress : String?
        get() = pref.getString(USER_ADDRESS,"").toString()
        set(value) = pref.edit().putString(USER_ADDRESS,value).apply()

    fun resetSharedPref(){
        context.getSharedPreferences(PREF_NAME,0).edit().clear().apply()
    }

}