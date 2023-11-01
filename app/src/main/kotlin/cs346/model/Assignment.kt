package cs346.model

import java.time.LocalDateTime

data class Assignment(
    var id: Int,
    var name: String? = null,
    var dueDate: LocalDateTime? = null,
    var score: Double? = null,
    var weight: Double? = null,
    var weightedMark: Double? = null,
    var createdDate: LocalDateTime = LocalDateTime.now(),
    var lastModifiedDate: LocalDateTime = LocalDateTime.now()
) {
    fun editName(newName: String? = null): Assignment {
        return copy(name = newName, lastModifiedDate = LocalDateTime.now())
    }

    fun editDueDate(newDueDate: LocalDateTime? = null): Assignment {
        return copy(dueDate = newDueDate, lastModifiedDate = LocalDateTime.now())
    }

    fun editScore(newScore: Double? = null): Assignment {
        return copy(
            score = newScore,
            weightedMark = weight?.let { newScore?.times(it / 100) },
            lastModifiedDate = LocalDateTime.now()
        )
    }

    fun editWeight(newWeight: Double? = null): Assignment {
        return copy(
            weight = newWeight,
            weightedMark = score?.let { newWeight?.times(it / 100) },
            lastModifiedDate = LocalDateTime.now()
        )
    }

    fun editWeightedMark(newWeightedMark: Double? = null): Assignment {
        return copy(weightedMark = newWeightedMark, lastModifiedDate = LocalDateTime.now())
    }
}

fun MutableList<Assignment>.findNextID(): Int {
    return (this.maxOfOrNull { it.id } ?: 0) + 1
}

fun MutableList<Assignment>.add(
    name: String? = null,
    dueDate: LocalDateTime? = null,
    score: Double? = null,
    weight: Double? = null,
) {
    this.add(
        Assignment(
            id = findNextID(),
            name = name,
            dueDate = dueDate,
            score = score,
            weight = weight,
            weightedMark = weight?.let { score?.times(it / 100) }
        )
    )
}

fun MutableList<Assignment>.editName(newName: String? = null, id: Int) {
    this.forEachIndexed { index, assignment ->
        if (assignment.id == id) {
            this[index] = this[index].editName(newName)
            return
        }
    }
}

fun MutableList<Assignment>.editDueDate(newDueDate: LocalDateTime? = null, id: Int) {
    this.forEachIndexed { index, assignment ->
        if (assignment.id == id) {
            this[index] = this[index].editDueDate(newDueDate)
            return
        }
    }
}

fun MutableList<Assignment>.editScore(newScore: Double? = null, id: Int) {
    this.forEachIndexed { index, assignment ->
        if (assignment.id == id) {
            this[index] = this[index].editScore(newScore)
            return
        }
    }
}

fun MutableList<Assignment>.editWeight(newWeight: Double? = null, id: Int) {
    this.forEachIndexed { index, assignment ->
        if (assignment.id == id) {
            this[index] = this[index].editWeight(newWeight)
            return
        }
    }
}

fun MutableList<Assignment>.editWeightedMark(newWeightedMark: Double? = null, id: Int) {
    this.forEachIndexed { index, assignment ->
        if (assignment.id == id) {
            this[index] = this[index].editWeightedMark(newWeightedMark)
            return
        }
    }
}
