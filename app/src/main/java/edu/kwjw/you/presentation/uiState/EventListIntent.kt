package edu.kwjw.you.presentation.uiState

sealed interface EventListIntent {
    data class GetEvents(val userId: Int) : EventListIntent
}