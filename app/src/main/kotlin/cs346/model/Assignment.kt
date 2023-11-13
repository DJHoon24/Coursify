package cs346.model

import cs346.views.theme.getLocalDateTime
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Assignment(
    @SerialName("id")
    var id: Int,
    @SerialName("courseId")
    var courseId: Int,
    @SerialName("name")
    var name: String = "",
    @SerialName("dueDate")
    var dueDate: String,
    @SerialName("score")
    var score: Float = 0f,
    @SerialName("weight")
    var weight: Float = 0f,
    @SerialName("weightedMark")
    var weightedMark: Float = 0f,
    @SerialName("createdDate")
    var createdDate: LocalDateTime = getLocalDateTime(),
    @SerialName("lastModifiedDate")
    var lastModifiedDate: LocalDateTime = getLocalDateTime()
) {
    fun editName(newName: String = ""): Assignment {
        val lastModifiedDate = getLocalDateTime()
        Db.database.assignmentQueries.updateName(newName, lastModifiedDate.toString(), id.toLong())
        return copy(name = newName, lastModifiedDate = lastModifiedDate)
    }

    fun editDueDate(newDueDate: String): Assignment {
        val lastModifiedDate = getLocalDateTime()
        Db.database.assignmentQueries.updateDueDate(newDueDate, lastModifiedDate.toString(), id.toLong())
        return copy(dueDate = newDueDate, lastModifiedDate = lastModifiedDate)
    }

    fun editScore(newScore: Float = 0f): Assignment {
        val weightedMark = weight.let { newScore.times(it / 100) }
        val lastModifiedDate = getLocalDateTime()
        Db.database.assignmentQueries.updateScore(newScore.toDouble(), weightedMark.toDouble(), lastModifiedDate.toString(), id.toLong())
        return copy(
            score = newScore,
            weightedMark = weightedMark,
            lastModifiedDate = lastModifiedDate
        )
    }

    fun editWeight(newWeight: Float = 0f): Assignment {
        val weightedMark = score.let { newWeight.times(it / 100) }
        val lastModifiedDate = getLocalDateTime()
        Db.database.assignmentQueries.updateWeight(newWeight.toDouble(), weightedMark.toDouble(), lastModifiedDate.toString(), id.toLong())
        return copy(
            weight = newWeight,
            weightedMark = weightedMark,
            lastModifiedDate = lastModifiedDate
        )
    }

    fun editWeightedMark(newWeightedMark: Float = 0f): Assignment {
        val lastModifiedDate = getLocalDateTime()
        Db.database.assignmentQueries.updateWeightedMark(newWeightedMark.toDouble(), lastModifiedDate.toString(), id.toLong())
        return copy(weightedMark = newWeightedMark, lastModifiedDate = lastModifiedDate)
    }

    fun deleteAssignment() {
        Db.database.assignmentQueries.deleteAssignment(this.id.toLong())
    }
}

fun MutableList<Assignment>.getById(
    id: Int
): Assignment? {
    this.forEachIndexed { index, assign ->
        if (assign.id == id) {
            return this[index]
        }
    }
    return null
}

// this function is not used in implementation
fun MutableList<Assignment>.add(
    courseId: Int,
    name: String = "",
    dueDate: String = "",
    score: Float = 0f,
    weight: Float = 0f,
    createdDate: LocalDateTime = getLocalDateTime(),
    lastModifiedDate: LocalDateTime = getLocalDateTime()
) {
    val weightedMark = (score * weight / 100)

    this.add(
        Assignment(
            id = -1,
            courseId = courseId,
            name = name,
            dueDate = dueDate,
            score = score,
            weight = weight,
            weightedMark = weightedMark,
            createdDate = createdDate,
            lastModifiedDate = lastModifiedDate
        )
    )
}

fun MutableList<Assignment>.editName(newName: String = "", id: Int) {
    this.forEachIndexed { index, assignment ->
        if (assignment.id == id) {
            this[index] = this[index].editName(newName)
            return
        }
    }
}

fun MutableList<Assignment>.editDueDate(newDueDate: String = "", id: Int) {
    this.forEachIndexed { index, assignment ->
        if (assignment.id == id) {
            this[index] = this[index].editDueDate(newDueDate)
            return
        }
    }
}

fun MutableList<Assignment>.editScore(newScore: Float = 0f, id: Int) {
    this.forEachIndexed { index, assignment ->
        if (assignment.id == id) {
            this[index] = this[index].editScore(newScore)
            return
        }
    }
}

fun MutableList<Assignment>.editWeight(newWeight: Float = 0f, id: Int) {
    this.forEachIndexed { index, assignment ->
        if (assignment.id == id) {
            this[index] = this[index].editWeight(newWeight)
            return
        }
    }
}

fun MutableList<Assignment>.editWeightedMark(newWeightedMark: Float = 0f, id: Int) {
    this.forEachIndexed { index, assignment ->
        if (assignment.id == id) {
            this[index] = this[index].editWeightedMark(newWeightedMark)
            return
        }
    }
}
