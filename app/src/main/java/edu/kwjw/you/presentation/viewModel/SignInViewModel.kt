package edu.kwjw.you.presentation.viewModel

import androidx.lifecycle.ViewModel
import edu.kwjw.you.presentation.uiState.SignInIntent
import edu.kwjw.you.presentation.uiState.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class SignInViewModel : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state

    fun processIntent(intent: SignInIntent) {
        when (intent) {
            SignInIntent.SignIn -> TODO()
            is SignInIntent.UpdateLogin -> updateLogin(login = intent.login)
            is SignInIntent.UpdatePassword -> updatePassword(password = intent.password)
        }
    }

    private fun updateLogin(login: String) {
        _state.update { state ->
            state.copy(
                login = login
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
}