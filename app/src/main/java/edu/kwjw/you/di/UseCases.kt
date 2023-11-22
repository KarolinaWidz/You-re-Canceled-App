package edu.kwjw.you.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.kwjw.you.data.repository.EventRepository
import edu.kwjw.you.domain.usecase.NewEvent
import edu.kwjw.you.domain.usecase.UserEvents
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCases {

    @Provides
    @Singleton
    fun provideEventListUseCase(repository: EventRepository): UserEvents {
        return UserEvents(repository)
    }

    @Provides
    @Singleton
    fun provideAddNewEvent(repository: EventRepository): NewEvent {
        return NewEvent(repository)
    }
}