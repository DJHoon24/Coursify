package cs346.model

import java.time.LocalDateTime

data class Note(
    var id: Int,
    var title: String? = null,
    var content: String? = null,
    var createdDate: LocalDateTime = LocalDateTime.now(),
    var lastModifiedDate: LocalDateTime = LocalDateTime.now()
) {
    fun editNote(newTitle: String? = null, newContent: String? = null): Note {
        return copy(title = newTitle, content = newContent, lastModifiedDate = LocalDateTime.now())
    }
}

fun MutableList<Note>.findNextID(): Int {
    return (this.maxOfOrNull { it.id } ?: 0) + 1
}

fun MutableList<Note>.add(title: String? = null, content: String? = null) {
    this.add(Note(id = findNextID(), title = title, content = content))
}

fun MutableList<Note>.edit(newTitle: String? = null, newContent: String? = null, id: Int) {
    this.forEachIndexed { index, note ->
        if (note.id == id) {
            this[index] = this[index].editNote(newTitle, newContent)
            return
        }
    }
}
