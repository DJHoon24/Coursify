package cs346.model

import androidx.compose.runtime.mutableStateListOf
import cs346.controller.APIController
import cs346.helper.toClass
import cs346.views.theme.getLocalDateTime
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Course(
    @SerialName("id")
    var id: Int,
    @SerialName("userId")
    var userId: Int,
    @SerialName("courseNumber")
    var courseNumber: String = "",
    @SerialName("lectureInfo")
    var lectureInfo: String = "",
    @SerialName("instructors")
    var instructors: String = "",
    @SerialName("courseDescription")
    var courseDescription: String = "",
    @SerialName("review")
    var review: String = "",
    @SerialName("rating")
    var rating: Int = 1,
    @SerialName("notes")
    var notes: MutableList<Note> = mutableStateListOf(),
    @SerialName("assignments")
    var assignments: MutableList<Assignment> = mutableStateListOf(),
    @SerialName("createdDate")
    var createdDate: LocalDateTime,
    @SerialName("lastModifiedDate")
    var lastModifiedDate: LocalDateTime,
) {
    companion object {

        data class ApiCourseData(
            val courseNumber: String = "",
            val courseDescription: String = "",
            val lectureInfo: String = "",)
        suspend fun createCourse(courseCode: String): ApiCourseData {
            val apiCourseData = CourseCatalogue.getCourse(courseCode)
            if (apiCourseData === null || apiCourseData.courseId === null) {
                return ApiCourseData(
                    courseNumber = courseCode,
                )
            }

            val lectureInfo = APIController.getCourseSchedule(courseId = apiCourseData.courseId)
            return ApiCourseData(
                courseNumber = courseCode,
                courseDescription = apiCourseData.description ?: "",
                lectureInfo = lectureInfo,
            )
        }
    }

    fun editCourseNumber(newCourseNumber: String = ""): Course {
        val lastModifiedDate = getLocalDateTime()
        Db.database.courseQueries.updateCourseNumber(newCourseNumber, lastModifiedDate.toString(), id.toLong())
        return copy(courseNumber = newCourseNumber, lastModifiedDate = lastModifiedDate)
    }

    fun editInstructors(newInstructors: String = ""): Course {
        val lastModifiedDate = getLocalDateTime()
        Db.database.courseQueries.updateInstructors(newInstructors, lastModifiedDate.toString(), id.toLong())
        return copy(instructors = newInstructors, lastModifiedDate = lastModifiedDate)
    }

    fun editLectureInfo(newLectureInfo: String = ""): Course {
        val lastModifiedDate = getLocalDateTime()
        Db.database.courseQueries.updateLectureInfo(newLectureInfo, lastModifiedDate.toString(), id.toLong())
        return copy(lectureInfo = newLectureInfo, lastModifiedDate = lastModifiedDate)
    }

    fun editCourseDescription(newCourseDescription: String = ""): Course {
        val lastModifiedDate = getLocalDateTime()
        Db.database.courseQueries.updateCourseDescription(newCourseDescription, lastModifiedDate.toString(), id.toLong())
        return copy(courseDescription = newCourseDescription, lastModifiedDate = lastModifiedDate)
    }

    fun editReview(newReview: String = ""): Course {
        val lastModifiedDate = getLocalDateTime()
        Db.database.courseQueries.updateReview(newReview, lastModifiedDate.toString(), id.toLong())
        return copy(review = newReview, lastModifiedDate = lastModifiedDate)
    }

    fun editRating(newRating: Int = 0): Course {
        val lastModifiedDate = getLocalDateTime()
        Db.database.courseQueries.updateRating(newRating.toLong(), lastModifiedDate.toString(), id.toLong())
        return copy(rating = newRating, lastModifiedDate = lastModifiedDate)
    }

    fun deleteCourse() {
        for (note in this.notes) {
            note.deleteNote()
        }
        for (assignment in this.assignments) {
            assignment.deleteAssignment()
        }
        Db.database.courseQueries.deleteCourse(this.id.toLong())
    }
}

fun MutableList<Course>.getById(
    id: Int
): Course? {
    this.forEachIndexed { index, course ->
        if (course.id == id) {
            return this[index]
        }
    }
    return null
}

// return the newly added course's id
fun MutableList<Course>.add(
    courseNumber: String = "",
    lectureInfo: String = "",
    instructors: String = "",
    courseDescription: String = "",
    review: String = "",
    rating: Int = 0,
): Int {
    var lastInsertCourse: cs346.sqldelight.Course
    var lastInsertCourseId = -1

    Db.database.transactionWithResult {
        Db.database.courseQueries.insertCourse(
            userId = User.id.toLong(),
            courseNumber = courseNumber,
            lectureInfo = lectureInfo,
            instructors = instructors,
            courseDescription = courseDescription,
            review = review,
            rating = rating.toLong(),
            createdDate = getLocalDateTime().toString(),
            lastModifiedDate = getLocalDateTime().toString()
        )
        lastInsertCourse = Db.database.courseQueries.lastInsertCourse().executeAsOne()
        lastInsertCourseId = Db.database.courseQueries.lastInsertCourseId().executeAsOne().toInt()

        add(lastInsertCourse.toClass())
    }

    return lastInsertCourseId
}

fun MutableList<Course>.deleteCourse(id: Int) {
    var currentCourse = this.getById(id)
    currentCourse?.deleteCourse()
}

fun MutableList<Course>.editCourseNumber(newCourseNumber: String = "", id: Int) {
    this.forEachIndexed { index, course ->
        if (course.id == id) {
            this[index] = this[index].editCourseNumber(newCourseNumber)
            return
        }
    }
}

fun MutableList<Course>.editLectureInfo(newLectureInfo: String = "", id: Int) {
    this.forEachIndexed { index, course ->
        if (course.id == id) {
            this[index] = this[index].editLectureInfo(newLectureInfo)
            return
        }
    }
}

fun MutableList<Course>.editInstructors(newInstructors: String, id: Int) {
    this.forEachIndexed { index, course ->
        if (course.id == id) {
            this[index] = this[index].editInstructors(newInstructors)
            return
        }
    }
}


fun MutableList<Course>.editCourseDescription(courseDescription: String = "", id: Int) {
    this.forEachIndexed { index, course ->
        if (course.id == id) {
            this[index] = this[index].editCourseDescription(courseDescription)
            return
        }
    }
}

fun MutableList<Course>.editReview(newReview: String = "", id: Int) {
    this.forEachIndexed { index, course ->
        if (course.id == id) {
            this[index] = this[index].editReview(newReview)
            return
        }
    }
}

fun MutableList<Course>.editRating(newRating: Int = 0, id: Int) {
    this.forEachIndexed { index, course ->
        if (course.id == id) {
            this[index] = this[index].editRating(newRating)
            return
        }
    }
}