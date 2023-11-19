package edu.kwjw.you.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.kwjw.you.data.repository.EventRepository
import edu.kwjw.you.domain.usecase.EventList
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCases {

    @Provides
    @Singleton
    fun provideEventListUseCase(repository: EventRepository): EventList {
        return EventList(repository)
    }
}