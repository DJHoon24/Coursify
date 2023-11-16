package cs346.model

import androidx.compose.runtime.mutableStateListOf

object User {
    var id: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var email: String = ""
    var courses = mutableStateListOf<Course>()
}
