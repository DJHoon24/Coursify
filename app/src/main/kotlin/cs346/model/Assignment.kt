package cs346.model

import java.time.LocalDateTime

data class Assignment(
        var id: Int,
        var name: String = "",
        var dueDate: LocalDateTime? = LocalDateTime.now(),
        var score: Float = 0f,
        var weight: Float = 0f,
        var weightedMark: Float = 0f,
        var createdDate: LocalDateTime = LocalDateTime.now(),
        var lastModifiedDate: LocalDateTime = LocalDateTime.now()
) {
    fun editName(newName: String = ""): Assignment {
        return copy(name = newName, lastModifiedDate = LocalDateTime.now())
    }

    fun editDueDate(newDueDate: LocalDateTime? = null): Assignment {
        return copy(dueDate = newDueDate, lastModifiedDate = LocalDateTime.now())
    }

    fun editScore(newScore: Float = 0f): Assignment {
        return copy(
            score = newScore,
                weightedMark = weight.let { newScore.times(it / 100) },
            lastModifiedDate = LocalDateTime.now()
        )
    }

    fun editWeight(newWeight: Float = 0f): Assignment {
        return copy(
            weight = newWeight,
                weightedMark = score.let { newWeight.times(it / 100) },
            lastModifiedDate = LocalDateTime.now()
        )
    }

    fun editWeightedMark(newWeightedMark: Float = 0f): Assignment {
        return copy(weightedMark = newWeightedMark, lastModifiedDate = LocalDateTime.now())
    }
}

fun MutableList<Assignment>.findNextID(): Int {
    return (this.maxOfOrNull { it.id } ?: 0) + 1
}

fun MutableList<Assignment>.add(
        name: String = "",
        dueDate: LocalDateTime? = LocalDateTime.now(),
        score: Float = 0f,
        weight: Float = 0f,
) {
    val weightedMark = (score * weight / 100)

    this.add(
        Assignment(
            id = findNextID(),
            name = name,
            dueDate = dueDate,
            score = score,
            weight = weight,
                weightedMark = weightedMark
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

fun MutableList<Assignment>.editDueDate(newDueDate: LocalDateTime? = null, id: Int) {
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
