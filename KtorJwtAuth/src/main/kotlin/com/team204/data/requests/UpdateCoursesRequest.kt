package com.team204.data.requests

import com.team204.data.models.courses.Course
import kotlinx.serialization.Serializable

@Serializable
data class UpdateCoursesRequest(
    val email: String,
    val courses: MutableList<Course>
)