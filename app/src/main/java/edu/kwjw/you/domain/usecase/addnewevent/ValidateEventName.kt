package edu.kwjw.you.domain.usecase.addnewevent

import android.util.Log
import edu.kwjw.you.domain.usecase.common.ValidationError
import edu.kwjw.you.domain.usecase.common.ValidationResult
import javax.inject.Inject

class ValidateEventName @Inject constructor() {
    fun execute(name: String): ValidationResult {
        if (name.isBlank()) {
            Log.w(LOG_TAG, "Name is blank")
            return ValidationResult(isSuccessful = false, error = ValidationError.Empty)
        }
        if (name.length > 100) {
            Log.w(LOG_TAG, "Maximum length of name exceeded")
            return ValidationResult(
                isSuccessful = false,
                error = ValidationError.MaxLengthExceeded
            )
        }
        return ValidationResult(isSuccessful = true)
    }

    companion object {
        const val LOG_TAG = "ValidateEventName"
    }
}