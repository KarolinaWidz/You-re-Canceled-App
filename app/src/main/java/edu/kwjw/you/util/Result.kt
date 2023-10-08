package edu.kwjw.you.util

sealed class Result<out T>{
    data class Success<out T>(val data:T): Result<T>()
    class HttpError(val code:Int): Result<Nothing>()
    class NetworkError(val exception: Exception) : Result<Nothing>()
}
