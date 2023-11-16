package cs346.model

import cs346.views.theme.dateFormat
import cs346.views.theme.getLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    var id: Int,
    var title: String,
    var content: String,
    var createdDateTime: String,
    var lastModifiedDateTime: String
) {
    fun editNote(newTitle: String = "", newContent: String = ""): Note {
        return copy(title = newTitle, content = newContent, lastModifiedDateTime = dateFormat(getLocalDateTime()))
    }
}

fun MutableList<Note>.findNextID(): Int {
    return (this.maxOfOrNull { it.id } ?: 0) + 1
}

fun MutableList<Note>.getById(
    id: Int
): Note? {
    this.forEachIndexed { index, note ->
        if (note.id == id) {
            return this[index]
        }
    }
    return null
}

fun MutableList<Note>.addNote(
    title: String = "",
    content: String = "",
    createdDateTime: String = dateFormat(getLocalDateTime()),
    lastModifiedDateTime: String = dateFormat(getLocalDateTime())
) {
    this.add(
        Note(
            id = findNextID(),
            title = title,
            content = content,
            createdDateTime = createdDateTime,
            lastModifiedDateTime = lastModifiedDateTime,
        )
    )
}

fun MutableList<Note>.edit(newTitle: String = "", newContent: String = "", id: Int) {
    this.forEachIndexed { index, note ->
        if (note.id == id) {
            this[index] = this[index].editNote(newTitle, newContent)
            return
        }
    }
}
