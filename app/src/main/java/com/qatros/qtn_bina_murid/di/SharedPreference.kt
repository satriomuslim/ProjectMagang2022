package com.qatros.qtn_bina_murid.di

import android.annotation.SuppressLint
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SharedPreference(val context: Context) {
    companion object {
        private const val PREF_NAME ="qatros.qtn_bina_murid.user_token"
        private const val USER_TOKEN = "user.token"

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


    fun resetSharedPref(){
        context.getSharedPreferences(PREF_NAME,0).edit().clear().apply()
    }



}