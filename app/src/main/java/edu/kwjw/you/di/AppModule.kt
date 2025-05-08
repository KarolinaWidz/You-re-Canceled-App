package edu.kwjw.you.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.kwjw.you.data.remote.EventService
import edu.kwjw.you.data.remote.UserAccountFirebaseService
import edu.kwjw.you.data.remote.UserAccountService
import edu.kwjw.you.data.remote.adapter.LocalDateTimeAdapter
import edu.kwjw.you.data.remote.adapter.UuidAdapter
import edu.kwjw.you.data.repository.EventOnlineRepository
import edu.kwjw.you.data.repository.EventRepository
import edu.kwjw.you.data.repository.UserAccountOnlineRepository
import edu.kwjw.you.data.repository.UserAccountRepository
import edu.kwjw.you.util.AuthInterceptor
import edu.kwjw.you.util.encrypteddatastore.UserPreferences
import edu.kwjw.you.util.encrypteddatastore.UserPreferencesSerializer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    private const val BASE_URL = "https://cancelled.jacekku.net/"
    private const val USER_PREFERENCES_FILE = "user_preferences"

    private val moshi =
        Moshi.Builder()
            .add(UuidAdapter())
            .add(LocalDateTimeAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor) = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideEventService(retrofit: Retrofit): EventService =
        retrofit.create(EventService::class.java)

    @Provides
    @Singleton
    fun provideUserAccountFirebaseService(): UserAccountService = UserAccountFirebaseService()

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(@ApplicationContext context: Context): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            produceFile = { context.dataStoreFile(USER_PREFERENCES_FILE) }
        )
    }
}

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindEventRepository(repository: EventOnlineRepository): EventRepository

    @Binds
    @Singleton
    abstract fun bindUserAccountRepository(repository: UserAccountOnlineRepository): UserAccountRepository
}