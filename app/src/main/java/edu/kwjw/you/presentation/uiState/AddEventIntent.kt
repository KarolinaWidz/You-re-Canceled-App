package edu.kwjw.you.presentation.uiState

sealed interface AddEventIntent{
    data class UpdateName(val name: String): AddEventIntent
}