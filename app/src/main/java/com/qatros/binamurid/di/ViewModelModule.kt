package com.qatros.binamurid.di

import com.qatros.binamurid.ui.chat.ChatViewModel
import com.qatros.binamurid.ui.history.HistoryViewModel
import com.qatros.binamurid.ui.login.LoginViewModel
import com.qatros.binamurid.ui.otp.OtpViewModel
import com.qatros.binamurid.ui.parent.child.FormChildViewModel
import com.qatros.binamurid.ui.parent.daily.DailyParentViewModel
import com.qatros.binamurid.ui.parent.home.HomeViewModel
import com.qatros.binamurid.ui.pedagogue.daily.DailyPedagogueViewModel
import com.qatros.binamurid.ui.pedagogue.home.HomePedagogueViewModel
import com.qatros.binamurid.ui.pedagogue.scanChildren.ScanChildrenViewModel
import com.qatros.binamurid.ui.profile.ProfileViewModel
import com.qatros.binamurid.ui.register.RegisterViewModel
import com.qatros.binamurid.ui.resetPassword.ResetPasswordViewModel
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