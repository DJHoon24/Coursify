package cs346.model

import androidx.compose.runtime.mutableStateListOf
import cs346.controller.UWOpenAPIController
import cs346.views.theme.dateFormat
import cs346.views.theme.getLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Course(
    var id: Int,
    var courseNumber: String,
    var lectureInfo: String,
    var instructors: String,
    var courseDescription: String,
    var review: String,
    var rating: Int,
    var notes: MutableList<Note> = mutableStateListOf(),
    var assignments: MutableList<Assignment> = mutableStateListOf(),
    var createdDate: String,
    var lastModifiedDate: String,
) {
    companion object {

        data class ApiCourseData(
            val courseNumber: String = "",
            val courseDescription: String = "",
            val lectureInfo: String = "",
        )

        suspend fun createCourse(courseCode: String): ApiCourseData {
            val apiCourseData = CourseCatalogue.getCourse(courseCode)
            if (apiCourseData === null || apiCourseData.courseId === null) {
                return ApiCourseData(
                    courseNumber = courseCode,
                )
            }

            val lectureInfo = UWOpenAPIController.getCourseSchedule(courseId = apiCourseData.courseId)
            return ApiCourseData(
                courseNumber = courseCode,
                courseDescription = apiCourseData.description ?: "",
                lectureInfo = lectureInfo,
            )
        }
    }

    fun editCourseNumber(newCourseNumber: String = ""): Course {
        return copy(courseNumber = newCourseNumber, lastModifiedDate = dateFormat(getLocalDateTime()))
    }

    fun editInstructors(newInstructors: String = ""): Course {
        return copy(instructors = newInstructors, lastModifiedDate = dateFormat(getLocalDateTime()))
    }

    fun editLectureInfo(newLectureInfo: String = ""): Course {
        return copy(lectureInfo = newLectureInfo, lastModifiedDate = dateFormat(getLocalDateTime()))
    }

    fun editCourseDescription(newCourseDescription: String = ""): Course {
        return copy(courseDescription = newCourseDescription, lastModifiedDate = dateFormat(getLocalDateTime()))
    }

    fun editReview(newReview: String = ""): Course {
        return copy(review = newReview, lastModifiedDate = dateFormat(getLocalDateTime()))
    }

    fun editRating(newRating: Int = 0): Course {
        return copy(rating = newRating, lastModifiedDate = dateFormat(getLocalDateTime()))
    }
}

fun MutableList<Course>.findNextID(): Int {
    return (this.maxOfOrNull { it.id } ?: 0) + 1
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
    createdDate: String = dateFormat(getLocalDateTime()),
    lastModifiedDate: String = dateFormat(getLocalDateTime())
) {
    this.add(
        Course(
            id = findNextID(),
            courseNumber = courseNumber,
            lectureInfo = lectureInfo,
            instructors = instructors,
            courseDescription = courseDescription,
            review = review,
            rating = rating,
            notes = mutableStateListOf(),
            assignments = mutableStateListOf(),
            createdDate = createdDate,
            lastModifiedDate = lastModifiedDate
        )
    )
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