package edu.kwjw.you.domain.exception

class UserNotFoundException : Exception("User has not been found")
class TokenRetrievalException : Exception("Could not retrieve the token")
