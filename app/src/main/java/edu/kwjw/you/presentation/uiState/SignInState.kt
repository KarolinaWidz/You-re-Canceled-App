package edu.kwjw.you.presentation.uiState

data class SignInState(
    val uiState: SignInUiState,
    val email: String = "",
    val password: String = ""
)

sealed interface SignInUiState {
    data object Loading : SignInUiState
    data object Error : SignInUiState
    data object Success : SignInUiState
}