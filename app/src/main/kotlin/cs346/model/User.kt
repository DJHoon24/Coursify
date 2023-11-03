package cs346.model

import androidx.compose.runtime.mutableStateListOf

object User {
    var firstName: String
    var lastName: String
    var email: String
    var password: String
    val courses = mutableStateListOf<Course>()

    init {
        firstName = "Jane"
        lastName = "Doe"
        email = "jd11@uwaterloo.ca"
        password = "password"
        courses.add(Course(
            id = 1,
            courseNumber = "CS 346",
            lectureInfo = "Wed, Fri | 2:30PM - 4:20PM | MC 2035",
            instructors = "Dr. Jeff Avery",
            courseDescription = "Introduction to full-stack application design and development. Students will work in project teams to design and build complete, working applications and services using standard tools. Topics include best-practices in design, development, testing, and deployment.",
            review = "Really cool prof",
            rating = 5).apply {
                this.notes.add(Note(
                    id = 1,
                    title = "Introduction",
                    content = "Pretty much simple intro this class."
                ))
                this.notes.add(Note(
                    id = 4,
                    title = "Software Process",
                    content = "Learning about software processes, especially in Kotlin."
                ))
                this.notes.add(Note(
                    id = 5,
                    title = "Planning",
                    content = "Lets group together and plan for the project."
                ))
            }
        )
        courses.add(Course(
            id = 2,
            courseNumber = "CS 370",
            lectureInfo = "Mon, Wed | 2:30PM - 4:20PM | MC 2035",
            instructors = "Dr. Justin Wan",
            courseDescription = "In this course, simple but realistic examples of scientific computations are used to introduce basic algorithms and modern hardware and software environments for numerical computing.",
            review = "Interesting topics",
            rating = 4,
        ))
        courses.add(Course(
            id = 3,
            courseNumber = "CS 341",
            lectureInfo = "Mon, Wed | 2:30PM - 4:20PM | MC 2035",
            instructors = "Dr. Alice Gao",
            courseDescription = "Learning about algorithms",
            review = "Interesting algorithms",
            rating = 4,
        ).apply{
            this.notes.add(Note(
                id = 3,
                title = "Baguette Baking",
                content = "We learned today about making and baking baguettes. Hon hon hon. J'aime le baguette."
            ))
        })
    }
}
