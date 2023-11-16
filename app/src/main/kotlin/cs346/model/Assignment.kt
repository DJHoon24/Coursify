package cs346.model

import cs346.views.theme.dateFormat
import cs346.views.theme.getLocalDateTime
import kotlinx.serialization.Serializable

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
) {
    fun editName(newName: String = ""): Assignment {
        return copy(name = newName, lastModifiedDate = dateFormat(getLocalDateTime()))
    }

    fun editDueDate(newDueDate: String): Assignment {
        return copy(dueDate = newDueDate, lastModifiedDate = dateFormat(getLocalDateTime()))
    }

    fun editScore(newScore: Float = 0f): Assignment {
        return copy(
            score = newScore,
            weightedMark = weight.let { newScore.times(it / 100) },
            lastModifiedDate = dateFormat(getLocalDateTime())
        )
    }

    fun editWeight(newWeight: Float = 0f): Assignment {
        return copy(
            weight = newWeight,
            weightedMark = score.let { newWeight.times(it / 100) },
            lastModifiedDate = dateFormat(getLocalDateTime())
        )
    }
}

fun MutableList<Assignment>.findNextID(): Int {
    return (this.maxOfOrNull { it.id } ?: 0) + 1
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
    name: String = "",
    dueDate: String = "",
    score: Float = 0f,
    weight: Float = 0f,
    createdDate: String = dateFormat(getLocalDateTime()),
    lastModifiedDate: String = dateFormat(getLocalDateTime())
) {
    val weightedMark = (score * weight / 100)

    this.add(
        Assignment(
            id = findNextID(),
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
