package com.team204.data.models.users

import com.team204.data.models.courses.Course

interface UserDataSource {
    suspend fun getUserByEmail(email: String): User?
    suspend fun insertUser(user: User): Boolean
    suspend fun updateCourses(email: String, courses: MutableList<Course>): Boolean
}