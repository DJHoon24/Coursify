package cs346.model

object User {
    var firstName: String
    var lastName: String
    var email: String
    var password: String
    val courses: MutableList<Course>

    init {
        firstName = "Jane"
        lastName = "Doe"
        email = "jd11@uwaterloo.ca"
        password = "password"

        courses = mutableListOf(
                Course(
                        id = 1,
                        courseNumber = "CS 346",
                        lectureInfo = "Wed, Fri | 2:30PM - 4:20PM | MC 2035",
                        instructors = "Dr. Jeff Avery",
                        courseDescription = "Introduction to full-stack application design and development. Students will work in project teams to design and build complete, working applications and services using standard tools. Topics include best-practices in design, development, testing, and deployment.",
                        review = "Really cool prof",
                        rating = 5,
                ),
                Course(
                        id = 2,
                        courseNumber = "CS 370",
                        lectureInfo = "Mon, Wed | 2:30PM - 4:20PM | MC 2035",
                        instructors = "Dr. Justin Wan",
                        courseDescription = "In this course, simple but realistic examples of scientific computations are used to introduce basic algorithms and modern hardware and software environments for numerical computing.",
                        review = "Interesting topics",
                        rating = 4,
                )
        )
    }


}
