package edu.kwjw.you.domain.usecase.addnewevent

import edu.kwjw.you.domain.usecase.common.ValidationError
import edu.kwjw.you.domain.usecase.common.ValidationResult
import javax.inject.Inject

class ValidateEventName @Inject constructor() {
    fun execute(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(isSuccessful = false, error = ValidationError.Empty)
        }
        if (name.length > 100) {
            return ValidationResult(
                isSuccessful = false,
                error = ValidationError.MaxLengthExceeded
            )
        }
        return ValidationResult(isSuccessful = true)
    }
}