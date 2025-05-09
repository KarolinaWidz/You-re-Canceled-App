package edu.kwjw.you.data.repository.user

import androidx.datastore.core.DataStore
import edu.kwjw.you.data.remote.UserAccountService
import edu.kwjw.you.util.encrypteddatastore.UserPreferences
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class UserAccountOnlineRepositoryTest {
    val dataStore = mockk<DataStore<UserPreferences>>(relaxed = true)

    @Test
    fun `successful sign in should save token in data store`() = runTest {
        // GIVEN the user repository
        val userAccountService = mockk<UserAccountService> {
            coEvery { signInWithEmail(any(), any()) } returns Result.success(TEST_TOKEN)
        }
        val repository = UserAccountOnlineRepository(userAccountService, dataStore)

        // WHEN signing in is successful
        val result = repository.signInWithEmail(TEST_EMAIL, TEST_PASSWORD)

        // THEN token is saved in datastore
        coVerify { dataStore.updateData(any()) }

        // AND the result is success
        assertTrue { result.isSuccess }
    }

    @Test
    fun `failed sign in should not save token in data store`() = runTest {
        // GIVEN the user repository
        val userAccountService = mockk<UserAccountService> {
            coEvery { signInWithEmail(any(), any()) } returns Result.failure(Exception())
        }
        val repository = UserAccountOnlineRepository(userAccountService, dataStore)

        // WHEN signing in is failure
        val result = repository.signInWithEmail(TEST_EMAIL, TEST_PASSWORD)

        // THEN token is not saved in datastore
        coVerify { dataStore.updateData(any()) wasNot Called }

        // AND the result is failure
        assertTrue { result.isFailure }
    }

    companion object {
        const val TEST_TOKEN = "token"
        const val TEST_EMAIL = "test@test.com"
        const val TEST_PASSWORD = "test-password"
    }

}