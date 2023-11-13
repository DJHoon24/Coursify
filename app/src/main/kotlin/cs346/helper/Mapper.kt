package cs346.helper

import cs346.model.Assignment
import cs346.model.Course
import cs346.model.Note
import kotlinx.datetime.LocalDateTime

fun cs346.sqldelight.Course.toClass(): Course {
    return Course(
        id = this.id.toInt(),
        userId = this.userId.toInt(),
        courseNumber = this.courseNumber,
        lectureInfo = this.lectureInfo,
        instructors = this.instructors,
        courseDescription = this.courseDescription,
        review = this.review,
        rating = this.rating.toInt(),
        createdDate = LocalDateTime.parse(this.createdDate),
        lastModifiedDate = LocalDateTime.parse(this.lastModifiedDate)
    )
}

fun cs346.sqldelight.Note.toClass(): Note {
    return Note(
        id = this.id.toInt(),
        courseId = this.courseId.toInt(),
        title = this.title,
        content = this.content,
        createdDateTime = LocalDateTime.parse(this.createdDateTime),
        lastModifiedDateTime = LocalDateTime.parse(this.lastModifiedDateTime)
    )
}

fun cs346.sqldelight.Assignment.toClass(): Assignment {
    return Assignment(
        id = this.id.toInt(),
        courseId = this.courseId.toInt(),
        name = this.name,
        dueDate = this.dueDate,
        score = this.score.toFloat(),
        weight = this.weight.toFloat(),
        weightedMark = this.weightedMark.toFloat(),
        createdDate = LocalDateTime.parse(this.createdDate),
        lastModifiedDate = LocalDateTime.parse(this.lastModifiedDate)
    )
}