package edu.kwjw.you.presentation.uiState

sealed interface SignInIntent {
    data class UpdateLogin(val login: String) : SignInIntent
    data class UpdatePassword(val password: String) : SignInIntent
    data object SignIn : SignInIntent
}