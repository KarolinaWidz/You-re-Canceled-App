package edu.kwjw.you.data.remote

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import edu.kwjw.you.domain.exception.TokenRetrievalException
import edu.kwjw.you.domain.exception.UserNotFoundException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

interface UserAccountService {
    suspend fun signInWithEmail(email: String, password: String): Result<String>
}

class UserAccountFirebaseService : UserAccountService {
    override suspend fun signInWithEmail(email: String, password: String): Result<String> {
        try {
            // todo replace results with custom results
            val user = Firebase.auth.signInWithEmailAndPassword(email, password).await().user
                ?: return Result.failure(UserNotFoundException())
            val token = user.getIdToken(true).await().token
                ?: return Result.failure(TokenRetrievalException())
            return Result.success(token)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            return Result.failure(e)
        }
    }
}