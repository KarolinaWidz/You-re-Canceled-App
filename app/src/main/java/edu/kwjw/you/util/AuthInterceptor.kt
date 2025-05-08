package edu.kwjw.you.util

import androidx.datastore.core.DataStore
import edu.kwjw.you.util.encrypteddatastore.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val dataStore: DataStore<UserPreferences>) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //todo do this better
        val token = runBlocking { dataStore.data.first().token }
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}