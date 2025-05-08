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
import edu.kwjw.you.util.encrypteddatastore.UserPreferences
import edu.kwjw.you.util.encrypteddatastore.UserPreferencesSerializer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    private const val BASE_URL = "http://192.168.100.6:8080"
    private const val USER_PREFERENCES_FILE = "user_preferences"

    private val moshi =
        Moshi.Builder()
            .add(UuidAdapter())
            .add(LocalDateTimeAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideEventService(): EventService = retrofit.create(EventService::class.java)

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