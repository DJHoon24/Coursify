package com.team204.data.requests

import kotlinx.serialization.Serializable

@Serializable 
data class SignInAuthRequest(
    val email: String,
    val password: String,
)
