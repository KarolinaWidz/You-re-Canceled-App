package edu.kwjw.you.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.kwjw.you.data.repository.EventRepository
import edu.kwjw.you.domain.usecase.AddNewEvent
import edu.kwjw.you.domain.usecase.GetUserEvent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCases {

    @Provides
    @Singleton
    fun provideEventListUseCase(repository: EventRepository): GetUserEvent {
        return GetUserEvent(repository)
    }

    @Provides
    @Singleton
    fun provideAddNewEvent(repository: EventRepository): AddNewEvent {
        return AddNewEvent(repository)
    }
}