package cs346.model.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class SignUpAuthRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
)