package com.team204.data.models.notes

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Note(
    var id: Int,
    var title: String,
    var content: String,
    var createdDateTime: String,
    var lastModifiedDateTime: String
)