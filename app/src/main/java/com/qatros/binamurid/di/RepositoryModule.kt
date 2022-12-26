package com.qatros.binamurid.di

import com.qatros.binamurid.data.AppRepository
import com.qatros.binamurid.data.local.LocalRepository
import com.qatros.binamurid.data.remote.RemoteRepository
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