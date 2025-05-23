package edu.kwjw.you.util.validation

import android.util.Log

object InputTextValidator {
    private const val LOG_TAG = "InputTextValidator"
    private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

    internal fun validateEventName(name: String): Result {
        return when {
            name.isBlank() -> {
                Log.d(LOG_TAG, "Name is blank")
                Result(isSuccessful = false, error = Error.Empty)
            }

            name.length > 100 -> {
                Log.d(LOG_TAG, "Maximum length of name exceeded")
                Result(isSuccessful = false, error = Error.MaxLengthExceeded)
            }

            else -> Result(isSuccessful = true)
        }
    }

    internal fun validateEmail(email: String): Result {
        return when {
            email.isBlank() -> {
                Log.d(LOG_TAG, "Email is blank")
                Result(isSuccessful = false, error = Error.Empty)
            }

            email.length > 100 -> {
                Log.d(LOG_TAG, "Maximum length of email exceeded")
                Result(isSuccessful = false, error = Error.MaxLengthExceeded)
            }

            !EMAIL_REGEX.matches(email) -> {
                Log.d(LOG_TAG, "Email doesn't match pattern")
                Result(isSuccessful = false, error = Error.IncorrectEmailFormat)
            }

            else -> Result(isSuccessful = true)
        }
    }

    data class Result(
        val isSuccessful: Boolean,
        val error: Error? = null
    )

    sealed interface Error {
        data object Empty : Error
        data object MaxLengthExceeded : Error
        data object IncorrectEmailFormat : Error
    }
}