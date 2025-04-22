package edu.kwjw.you.data.repository

import edu.kwjw.you.data.remote.UserAccountService
import javax.inject.Inject

class UserAccountOnlineRepository @Inject constructor(
    private val userAccountService: UserAccountService,
) : UserAccountRepository {
    override suspend fun signInWithEmail(email: String, password: String): Result<Unit> {
        userAccountService.signInWithEmail(email = email, password = password)
            .onSuccess { token ->
                // todo add token manager and saving here
                // todo add interceptor
                //todo add moving to the next screen
                return Result.success(Unit)
            }.onFailure { e ->
                return Result.failure(e)
            }
        return Result.failure(Exception("e"))
    }

}