package edu.kwjw.you.presentation.uiState

import edu.kwjw.you.util.validation.InputTextValidator

data class SignInState(
    val uiState: SignInUiState,
    val email: String = "",
    val isEmailError: Boolean = false,
    val emailErrorType: InputTextValidator.Error? = null,
    val password: String = "",
    val isPasswordError: Boolean = false,
    val passwordErrorType: InputTextValidator.Error? = null
)

sealed interface SignInUiState {
    //todo add loading for button
    data object Idle : SignInUiState
    data object Success : SignInUiState
}

sealed interface SideEffect {
    data class ShowErrorSnackbar(val errorType: ErrorType) : SideEffect {
        enum class ErrorType {
            FORM_ERROR,
            SIGN_IN_ERROR
        }
    }
}