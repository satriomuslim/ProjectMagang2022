package com.qatros.qtn_bina_murid.di

import com.qatros.qtn_bina_murid.ui.login.LoginViewModel
import com.qatros.qtn_bina_murid.ui.parent.daily.DailyParentViewModel
import com.qatros.qtn_bina_murid.ui.parent.history.HistoryParentViewModel
import com.qatros.qtn_bina_murid.ui.parent.home.HomeViewModel
import com.qatros.qtn_bina_murid.ui.parent.profile.ProfileViewModel
import com.qatros.qtn_bina_murid.ui.pedagogue.daily.DailyPedagogueViewModel
import com.qatros.qtn_bina_murid.ui.pedagogue.history.HistoryPedagogueViewModel
import com.qatros.qtn_bina_murid.ui.pedagogue.home.HomePedagogueViewModel
import com.qatros.qtn_bina_murid.ui.pedagogue.profile.ProfilePedagogueViewModel
import com.qatros.qtn_bina_murid.ui.register.RegisterViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { LoginViewModel(get()) }
    single { RegisterViewModel(get()) }
    single { DailyParentViewModel(get()) }
    single { HistoryParentViewModel(get()) }
    single { HomeViewModel(get()) }
    single { ProfileViewModel(get()) }
    single { DailyPedagogueViewModel(get()) }
    single { HistoryPedagogueViewModel(get()) }
    single { HomePedagogueViewModel(get()) }
    single { ProfilePedagogueViewModel(get()) }


}