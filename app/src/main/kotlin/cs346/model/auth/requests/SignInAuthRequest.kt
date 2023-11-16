package cs346.model.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class SignInAuthRequest(
    val email: String,
    val password: String
)