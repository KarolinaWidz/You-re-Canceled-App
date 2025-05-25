package edu.kwjw.you.presentation.viewModel

import android.util.Log
import edu.kwjw.you.data.repository.user.UserAccountFakeRepository
import edu.kwjw.you.presentation.uiState.SignInIntent
import edu.kwjw.you.presentation.uiState.SignInUiState
import edu.kwjw.you.util.validation.InputTextValidator
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@OptIn(ExperimentalCoroutinesApi::class)
class SignInViewModelTest {

    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
    val userAccountRepository = UserAccountFakeRepository()
    val viewModel = SignInViewModel(userAccountRepository)

    @BeforeAll
    fun setup() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }

    @AfterAll
    fun cleanup() {
        unmockkObject(Log::class)
    }

    @BeforeEach
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun cleanupDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should not indicate error for valid email`() {
        // GIVEN the correct email
        val email = "test@domain.com"

        // WHEN it's used for updating
        viewModel.processIntent(SignInIntent.UpdateEmail(email))

        // THEN the viewmodel state reflects the changes
        assertEquals(email, viewModel.state.value.email)

        // AND no error is indicated
        assertFalse(viewModel.state.value.isEmailError)
        assertNull(viewModel.state.value.emailErrorType)
    }

    @Test
    fun `should indicate error for blank email`() {
        // GIVEN the blank email
        val email = " "

        // WHEN it's used for updating
        viewModel.processIntent(SignInIntent.UpdateEmail(email))

        // THEN the viewmodel state reflects the changes
        assertEquals(email, viewModel.state.value.email)

        // AND the error is indicated with Empty type
        assertTrue(viewModel.state.value.isEmailError)
        assertEquals(InputTextValidator.Error.Empty, viewModel.state.value.emailErrorType)
    }

    @Test
    fun `should indicate error for too long email`() {
        // GIVEN the too long email
        val email =
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean ma"

        // WHEN it's used for updating
        viewModel.processIntent(SignInIntent.UpdateEmail(email))

        // THEN the viewmodel state reflects the changes
        assertEquals(email, viewModel.state.value.email)

        // AND the error is indicated with MaxLengthExceeded type
        assertTrue(viewModel.state.value.isEmailError)
        assertEquals(
            InputTextValidator.Error.MaxLengthExceeded,
            viewModel.state.value.emailErrorType
        )
    }

    @Test
    fun `should indicate error for incorrect email format`() {
        // GIVEN the incorrect email
        val email = "testtest"

        // WHEN it's used for updating
        viewModel.processIntent(SignInIntent.UpdateEmail(email))

        // THEN the viewmodel state reflects the changes
        assertEquals(email, viewModel.state.value.email)

        // AND the error is indicated with IncorrectEmailFormat type
        assertTrue(viewModel.state.value.isEmailError)
        assertEquals(
            InputTextValidator.Error.IncorrectEmailFormat,
            viewModel.state.value.emailErrorType
        )
    }

    @Test
    fun `should not indicate error for non-empty password`() {
        // GIVEN the non-empty password
        val password = "password"

        // WHEN it's used for updating
        viewModel.processIntent(SignInIntent.UpdatePassword(password))

        // THEN the viewmodel state reflects the changes
        assertEquals(password, viewModel.state.value.password)

        // AND no error is indicated
        assertFalse(viewModel.state.value.isPasswordError)
        assertNull(viewModel.state.value.passwordErrorType)
    }

    @Test
    fun `should indicate error for empty password`() {
        // GIVEN the non-empty password
        val password = ""

        // WHEN it's used for updating
        viewModel.processIntent(SignInIntent.UpdatePassword(password))

        // THEN the viewmodel state reflects the changes
        assertEquals(password, viewModel.state.value.password)

        // AND error is indicated
        assertTrue(viewModel.state.value.isPasswordError)
        assertEquals(
            InputTextValidator.Error.Empty,
            viewModel.state.value.passwordErrorType
        )
    }

    @Test
    fun `should set ui to idle at init`() {
        // GIVEN view model is init
        val viewModel = SignInViewModel(userAccountRepository)
        // THEN UI state is set to idle
        assertEquals(SignInUiState.Idle, viewModel.state.value.uiState)
    }

    @Test
    fun `should set ui error when there is an email error`() {
        // GIVEN the email error
        val email = ""
        viewModel.processIntent(SignInIntent.UpdateEmail(email))

        // WHEN user is signing in
        viewModel.processIntent(SignInIntent.SignIn)

        // THEN UI state is set to error
        assertEquals(SignInUiState.Error, viewModel.state.value.uiState)
    }

    @Test
    fun `should set ui error when there is a password error`() {
        // GIVEN the password error
        val password = ""
        viewModel.processIntent(SignInIntent.UpdatePassword(password))

        // WHEN user is signing in
        viewModel.processIntent(SignInIntent.SignIn)

        // THEN UI state is set to error
        assertEquals(SignInUiState.Error, viewModel.state.value.uiState)
    }

    @Test
    fun `should set ui error when there is an email and password error`() {
        // GIVEN the email error
        val email = ""
        // AND the password error
        val password = ""

        viewModel.processIntent(SignInIntent.UpdateEmail(email))
        viewModel.processIntent(SignInIntent.UpdatePassword(password))

        // WHEN user is signing in
        viewModel.processIntent(SignInIntent.SignIn)

        // THEN UI state is set to error
        assertEquals(SignInUiState.Error, viewModel.state.value.uiState)
    }

    @Test
    fun `should set ui error when signing in unsuccessful`() = runTest {
        // GIVEN the correct email
        val email = "example@example.com"
        // AND the correct password
        val password = "test"

        viewModel.processIntent(SignInIntent.UpdateEmail(email))
        viewModel.processIntent(SignInIntent.UpdatePassword(password))

        // WHEN user is signing in
        // AND there is no user
        viewModel.processIntent(SignInIntent.SignIn)

        // THEN UI state is set to error
        assertEquals(SignInUiState.Error, viewModel.state.value.uiState)
    }

    @Test
    fun `should set ui success when signing in successful`() = runTest {
        // GIVEN the correct email
        val email = "test@test.com"
        // AND the correct password
        val password = "password"

        viewModel.processIntent(SignInIntent.UpdateEmail(email))
        viewModel.processIntent(SignInIntent.UpdatePassword(password))

        // WHEN user is signing in
        // AND there is no user
        viewModel.processIntent(SignInIntent.SignIn)

        // THEN UI state is set to error
        assertEquals(SignInUiState.Success, viewModel.state.value.uiState)
    }
}