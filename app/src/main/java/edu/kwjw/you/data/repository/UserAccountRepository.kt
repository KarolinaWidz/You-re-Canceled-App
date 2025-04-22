package edu.kwjw.you.data.repository

interface UserAccountRepository {
    suspend fun signInWithEmail(email: String, password: String): Result<Unit>
}