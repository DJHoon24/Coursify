package com.team204.data.models.courses

import com.team204.data.models.assignments.Assignment
import com.team204.data.models.notes.Note
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Course(
    var id: Int,
    var courseNumber: String,
    var lectureInfo: String,
    var instructors: String,
    var courseDescription: String,
    var review: String,
    var rating: Int,
    var notes: MutableList<Note>,
    var assignments: MutableList<Assignment>,
    var createdDate: String,
    var lastModifiedDate: String,
)