package com.geticons.API

public sealed class NetworkCall<out T > {
    data class Success<out T>(val response: T) : NetworkCall<T>()
    data class Error(val code: Int, val exception: String) : NetworkCall<Nothing>()
    object Loading : NetworkCall<Nothing>()
}