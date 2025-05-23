package edu.kwjw.you.presentation.uiState

sealed interface SignInIntent {
    data class UpdateEmail(val login: String) : SignInIntent
    data class UpdatePassword(val password: String) : SignInIntent
    data object SignIn : SignInIntent
}