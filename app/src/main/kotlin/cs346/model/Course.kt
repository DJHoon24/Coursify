package cs346.model

import androidx.compose.runtime.mutableStateListOf
import java.time.LocalDateTime

data class Course(
    var id: Int,
    var courseNumber: String = "",
    var lectureInfo: String = "",
    var instructors: String = "",
    var courseDescription: String = "",
    var review: String = "",
    var rating: Int = 1,
    var notes: MutableList<Note> = mutableStateListOf(),
    var assignments: MutableList<Assignment> = mutableStateListOf(),
    var createdDate: LocalDateTime = LocalDateTime.now(),
    var lastModifiedDate: LocalDateTime = LocalDateTime.now()
) {
    fun editCourseNumber(newCourseNumber: String = ""): Course {
        return copy(courseNumber = newCourseNumber, lastModifiedDate = LocalDateTime.now())
    }

    fun editInstructors(newInstructors: String = ""): Course {
        return copy(instructors = newInstructors, lastModifiedDate = LocalDateTime.now())
    }

    fun editLectureInfo(newLectureInfo: String = ""): Course {
        return copy(lectureInfo = newLectureInfo, lastModifiedDate = LocalDateTime.now())
    }

    fun editCourseDescription(newCourseDescription: String = ""): Course {
        return copy(courseDescription = newCourseDescription, lastModifiedDate = LocalDateTime.now())
    }

    fun editReview(newReview: String = ""): Course {
        return copy(review = newReview, lastModifiedDate = LocalDateTime.now())
    }

    fun editRating(newRating: Int = 0): Course {
        return copy(rating = newRating, lastModifiedDate = LocalDateTime.now())
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

fun MutableList<Course>.add(
        courseNumber: String = "",
        lectureInfo: String = "",
        instructors: String = "",
        courseDescription: String = "",
        review: String = "",
        rating: Int = 0,
) {
    this.add(
            Course(
                    id = findNextID(),
                    courseNumber = courseNumber,
                    lectureInfo = lectureInfo,
                    instructors = instructors,
                    courseDescription = courseDescription,
                    review = review,
                    rating = rating
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