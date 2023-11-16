package com.team204.data.responses

import com.team204.data.models.courses.Course
import com.team204.data.models.users.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val token: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val courses: MutableList<Course>,
)
