package edu.kwjw.you.data.repository.exception

class UserNotFoundException : Exception("User has not been found")
class TokenRetrievalException : Exception("Could not retrieve the token")
