package com.qatros.qtn_bina_murid.di

import com.qatros.qtn_bina_murid.data.AppRepository
import com.qatros.qtn_bina_murid.data.local.LocalRepository
import com.qatros.qtn_bina_murid.data.remote.RemoteRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        AppRepository(get(), get())
    }

    single {
        RemoteRepository(get())
    }

    single {
        LocalRepository(get())
    }
}