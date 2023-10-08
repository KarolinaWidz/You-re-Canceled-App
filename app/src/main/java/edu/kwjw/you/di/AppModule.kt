package edu.kwjw.you.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.kwjw.you.data.repository.EventRepository
import edu.kwjw.you.data.repository.network.EventOnlineRepository
import edu.kwjw.you.data.repository.network.EventService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    private const val BASE_URL = "http://10.0.2.2:8080"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideEventService(): EventService = retrofit.create(EventService::class.java)

}

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(repository: EventOnlineRepository): EventRepository
}