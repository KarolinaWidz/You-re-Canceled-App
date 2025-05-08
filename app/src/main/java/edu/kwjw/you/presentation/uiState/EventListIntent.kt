package edu.kwjw.you.presentation.uiState

sealed interface EventListIntent {
    data object GetEvents : EventListIntent
}