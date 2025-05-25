package edu.kwjw.you.data.repository.user

import edu.kwjw.you.data.repository.exception.UserNotFoundException

class UserAccountFakeRepository : UserAccountRepository {

    private val validUsers = mapOf(
        "test@test.com" to "password"
    )

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): Result<Unit> = if (validUsers[email] == password) {
        Result.success(Unit)
    } else {
        Result.failure(UserNotFoundException())
    }
}