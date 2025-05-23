package edu.kwjw.you.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.data.repository.user.UserAccountRepository
import edu.kwjw.you.presentation.uiState.SignInIntent
import edu.kwjw.you.presentation.uiState.SignInState
import edu.kwjw.you.presentation.uiState.SignInUiState
import edu.kwjw.you.util.validation.InputTextValidator.validateEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userAccountRepository: UserAccountRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState(uiState = SignInUiState.Idle))
    val state: StateFlow<SignInState> = _state

    fun processIntent(intent: SignInIntent) {
        when (intent) {
            SignInIntent.SignIn -> signIn()
            is SignInIntent.UpdateEmail -> updateEmail(email = intent.login)
            is SignInIntent.UpdatePassword -> updatePassword(password = intent.password)
        }
    }

    private fun updateEmail(email: String) {
        val result = validateEmail(email)
        _state.update { state ->
            state.copy(
                email = email,
                isEmailError = !result.isSuccessful,
                emailErrorType = result.error
            )
        }
    }

    private fun updatePassword(password: String) {
        //todo add password validation
        _state.update { state ->
            state.copy(
                password = password
            )
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            userAccountRepository.signInWithEmail(
                state.value.email,
                state.value.password
            ).onSuccess {
                _state.update { state -> state.copy(uiState = SignInUiState.Success) }
                Log.d(LOG_TAG, "User successfully signed in")
            }.onFailure {
                _state.update { state -> state.copy(uiState = SignInUiState.Error) }
                Log.d(LOG_TAG, "Error during signing in")
            }
        }
    }

    companion object {
        private const val LOG_TAG = "SignInViewModel"
    }
}