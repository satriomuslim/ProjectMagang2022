package com.qatros.qtn_bina_murid.di

import androidx.room.Room
import com.qatros.qtn_bina_murid.data.local.LocalDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single {
        Room.databaseBuilder(androidContext(), LocalDatabase::class.java, "db")
            .build()
    }
}