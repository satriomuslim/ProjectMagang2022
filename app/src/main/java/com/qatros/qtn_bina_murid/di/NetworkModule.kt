package com.qatros.qtn_bina_murid.di

import com.qatros.qtn_bina_murid.api.ApiService
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module{
    single{
        OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .build()
    }
    single{
        Retrofit.Builder()
            .baseUrl("https://binamurid.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(get())
            .build()
    }
    single{
        createWebService<ApiService>(get())
    }
}
inline fun <reified T>createWebService(retrofit: Retrofit):T = retrofit.create(T::class.java)