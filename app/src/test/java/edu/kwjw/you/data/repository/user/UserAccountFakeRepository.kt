package edu.kwjw.you.data.repository.user

class UserAccountFakeRepository: UserAccountRepository {
    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): Result<Unit> {
        TODO("Not yet implemented")
    }
}