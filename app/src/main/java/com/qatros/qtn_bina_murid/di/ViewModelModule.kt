package com.qatros.qtn_bina_murid.di

import com.qatros.qtn_bina_murid.ui.chat.ChatViewModel
import com.qatros.qtn_bina_murid.ui.history.HistoryViewModel
import com.qatros.qtn_bina_murid.ui.login.LoginViewModel
import com.qatros.qtn_bina_murid.ui.otp.OtpViewModel
import com.qatros.qtn_bina_murid.ui.parent.child.FormChildViewModel
import com.qatros.qtn_bina_murid.ui.parent.daily.DailyParentViewModel
import com.qatros.qtn_bina_murid.ui.parent.home.HomeViewModel
import com.qatros.qtn_bina_murid.ui.pedagogue.daily.DailyPedagogueViewModel
import com.qatros.qtn_bina_murid.ui.pedagogue.home.HomePedagogueViewModel
import com.qatros.qtn_bina_murid.ui.pedagogue.scanChildren.ScanChildrenViewModel
import com.qatros.qtn_bina_murid.ui.profile.ProfileViewModel
import com.qatros.qtn_bina_murid.ui.register.RegisterViewModel
import com.qatros.qtn_bina_murid.ui.resetPassword.ResetPasswordViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { LoginViewModel(get()) }
    single { RegisterViewModel(get()) }
    single { DailyParentViewModel(get()) }
    single { HistoryViewModel(get()) }
    single { HomeViewModel(get()) }
    single { ProfileViewModel(get()) }
    single { DailyPedagogueViewModel(get()) }
    single { HomePedagogueViewModel(get()) }
    single { ResetPasswordViewModel(get()) }
    single { FormChildViewModel(get()) }
    single { ScanChildrenViewModel(get()) }
    single { ChatViewModel(get()) }
    single { OtpViewModel(get()) }
}