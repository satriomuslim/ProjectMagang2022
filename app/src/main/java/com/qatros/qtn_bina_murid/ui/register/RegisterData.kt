package com.qatros.qtn_bina_murid.ui.register

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterData(
    var fullName : String,
    var email : String,
    var password : String
) : Parcelable