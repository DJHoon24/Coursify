package cs346.model

import cs346.helper.toClass
import cs346.views.theme.getLocalDateTime
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    @SerialName("id")
    var id: Int,
    @SerialName("courseId")
    var courseId: Int,
    @SerialName("title")
    var title: String = "",
    @SerialName("content")
    var content: String = "",
    @SerialName("createdDateTime")
    var createdDateTime: LocalDateTime,
    @SerialName("lastModifiedDateTime")
    var lastModifiedDateTime: LocalDateTime
) {
    fun editNote(newTitle: String = "", newContent: String = ""): Note {
        val lastModifiedDateTime = getLocalDateTime()
        Db.database.noteQueries.updateNote(
            newTitle,
            newContent,
            lastModifiedDateTime.toString(),
            id.toLong()
        )
        return copy(title = newTitle, content = newContent, lastModifiedDateTime = getLocalDateTime())
    }

    fun deleteNote() {
        Db.database.noteQueries.deleteNote(this.id.toLong())
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

fun MutableList<Note>.addNote(courseId: Int, title: String = "", content: String = "") {
    Db.database.transactionWithResult {
        Db.database.noteQueries.insertNote(
            courseId = courseId.toLong(),
            title = title,
            content = content,
            createdDateTime = getLocalDateTime().toString(),
            lastModifiedDateTime = getLocalDateTime().toString()
        )
        val lastInsertNote = Db.database.noteQueries.lastInsertNote().executeAsOne()
        add(lastInsertNote.toClass())
    }
}

fun MutableList<Note>.edit(newTitle: String = "", newContent: String = "", id: Int) {
    this.forEachIndexed { index, note ->
        if (note.id == id) {
            this[index] = this[index].editNote(newTitle, newContent)
            return
        }
    }
}
