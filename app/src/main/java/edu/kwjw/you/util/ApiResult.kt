package edu.kwjw.you.util

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    class HttpError(val code: Int) : ApiResult<Nothing>()
    class NetworkError(val exception: Exception) : ApiResult<Nothing>()
}
