package com.qatros.qtn_bina_murid

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qatros.qtn_bina_murid.di.networkModule
import com.qatros.qtn_bina_murid.di.repositoryModule
import com.qatros.qtn_bina_murid.di.roomModule
import com.qatros.qtn_bina_murid.di.viewModelModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(listOf(networkModule, repositoryModule, roomModule, viewModelModule))
        }
    }
}