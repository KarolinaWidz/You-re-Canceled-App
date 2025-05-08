package edu.kwjw.you.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.data.repository.UserAccountRepository
import edu.kwjw.you.presentation.uiState.SignInIntent
import edu.kwjw.you.presentation.uiState.SignInState
import edu.kwjw.you.presentation.uiState.SignInUiState
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
            is SignInIntent.UpdateLogin -> updateLogin(login = intent.login)
            is SignInIntent.UpdatePassword -> updatePassword(password = intent.password)
        }
    }

    private fun updateLogin(login: String) {
        _state.update { state ->
            state.copy(
                email = login
            )
        }
    }

    private fun updatePassword(password: String) {
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