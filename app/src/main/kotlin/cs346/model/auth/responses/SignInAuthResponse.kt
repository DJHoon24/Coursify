package cs346.model.auth.responses

import cs346.model.Course
import kotlinx.serialization.Serializable

@Serializable
data class SignInAuthResponse(
    val token: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val courses: MutableList<Course>
)