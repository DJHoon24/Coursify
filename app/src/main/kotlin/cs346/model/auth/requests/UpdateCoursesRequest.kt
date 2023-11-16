package cs346.model.auth.requests

import cs346.model.Course
import kotlinx.serialization.Serializable

@Serializable
data class UpdateCoursesRequest(
    val email: String,
    val courses: MutableList<Course>
)