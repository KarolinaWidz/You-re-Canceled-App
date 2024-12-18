package edu.kwjw.you.domain.usecase.common

data class ValidationResult(
    val isSuccessful: Boolean,
    val error: ValidationError? = null
)

sealed interface ValidationError {
    data object Empty : ValidationError
    data object MaxLengthExceeded: ValidationError
}