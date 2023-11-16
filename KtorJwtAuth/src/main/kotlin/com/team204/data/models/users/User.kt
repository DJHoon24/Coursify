package com.team204.data.models.users

import com.team204.data.models.courses.Course
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class User(
    @BsonId val id: String = ObjectId().toString(),
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val courses: MutableList<Course>,
    val salt: String
)