package edu.kwjw.you.data.repository.user

interface UserAccountRepository {
    suspend fun signInWithEmail(email: String, password: String): Result<Unit>
}