package com.qatros.binamurid.di

import androidx.room.Room
import com.qatros.binamurid.data.local.LocalDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single {
        Room.databaseBuilder(androidContext(), LocalDatabase::class.java, "db")
            .build()
    }
}