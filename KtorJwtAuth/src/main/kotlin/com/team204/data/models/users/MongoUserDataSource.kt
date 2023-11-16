package com.team204.data.models.users

import com.team204.data.models.courses.Course
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class MongoUserDataSource(
    db: CoroutineDatabase
): UserDataSource {

    private val users = db.getCollection<User>()

    override suspend fun getUserByEmail(email: String): User? {
        return users.findOne(User::email eq email)
    }

    override suspend fun insertUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }

    override suspend fun updateCourses(email: String, courses: MutableList<Course>): Boolean {
        return users.updateOne(User::email eq email, setValue(User::courses, courses)).wasAcknowledged()
    }
}