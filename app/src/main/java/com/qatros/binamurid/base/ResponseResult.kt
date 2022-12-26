package com.qatros.binamurid.base

import javax.net.ssl.HttpsURLConnection

sealed interface ResponseResult<out T> {
    class Success<T>(val data: T) : ResponseResult<T>
    class Error<T>(val code: Int = HttpsURLConnection.HTTP_INTERNAL_ERROR, val errorMsg: String?) : ResponseResult<T>
}