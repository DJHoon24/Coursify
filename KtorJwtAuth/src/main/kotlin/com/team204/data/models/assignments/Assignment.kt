package com.team204.data.models.assignments

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Assignment(
    var id: Int,
    var name: String,
    var dueDate: String,
    var score: Float,
    var weight: Float,
    var weightedMark: Float,
    var createdDate: String,
    var lastModifiedDate: String
)