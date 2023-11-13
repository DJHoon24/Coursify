package cs346.model

import androidx.compose.runtime.mutableStateListOf
import cs346.helper.toClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
object User {
    @SerialName("id")
    var id: Int
    @SerialName("firstName")
    var firstName: String
    @SerialName("lastName")
    var lastName: String
    @SerialName("email")
    var email: String
    @SerialName("password")
    var password: String
    @SerialName("courses")
    val courses = mutableStateListOf<Course>()

    init {
        val curUser = Db.database.userQueries.selectUser(1) // hardcoded id
        id = curUser.executeAsOne().id.toInt()
        firstName = curUser.executeAsOne().firstName
        lastName = curUser.executeAsOne().lastName
        email = curUser.executeAsOne().email
        password = curUser.executeAsOne().password

        // how to add courses to user? Should we pull from User or Course?
        val courseQueries = Db.database.courseQueries
        courses.addAll(courseQueries.selectAllCoursesByUserId(1).executeAsList().map { it.toClass() })
        courses.forEach {
            val noteQueries = Db.database.noteQueries
            it.notes.addAll(noteQueries.selectAllNotesByCourseId(it.id.toLong()).executeAsList().map { it.toClass() })
            val assignmentQueries = Db.database.assignmentQueries
            it.assignments.addAll(assignmentQueries.selectAllAssignmentsByCourseId(it.id.toLong()).executeAsList().map { it.toClass() })
        }
    }
}
