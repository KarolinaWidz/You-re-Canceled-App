package edu.kwjw.you.presentation.uiState

import edu.kwjw.you.presentation.ui.addnewevent.Time

sealed interface AddEventIntent{
    data class UpdateName(val name: String): AddEventIntent
    data class UpdateDate(val timestamp: Long?): AddEventIntent
    data class UpdateTime(val time: Time?): AddEventIntent
}