package edu.kwjw.you.presentation.uiState

data class SignInState(
    val uiState: SignInUiState,
    val email: String = "",
    val password: String = ""
)

sealed interface SignInUiState {
    //todo add loading for button
    data object Idle : SignInUiState
    data object Error : SignInUiState
    data object Success : SignInUiState
}