package edu.kwjw.you.data.repository.user

import androidx.datastore.core.DataStore
import edu.kwjw.you.data.remote.UserAccountService
import edu.kwjw.you.util.encrypteddatastore.UserPreferences
import javax.inject.Inject

class UserAccountOnlineRepository @Inject constructor(
    private val userAccountService: UserAccountService,
    private val dataStore: DataStore<UserPreferences>
) : UserAccountRepository {
    override suspend fun signInWithEmail(email: String, password: String): Result<Unit> {
        return userAccountService.signInWithEmail(email = email, password = password).fold(
            onSuccess =
                { token ->
                    dataStore.updateData {
                        UserPreferences(token = token)
                    }
                    return Result.success(Unit)
                },
            onFailure = { e ->
                return Result.failure(e)
            }
        )
    }
}