package cs346.model

import cs346.model.api.UWOpenAPICourse

object CourseCatalogue {
    private val courseMap = HashMap<String, UWOpenAPICourse>()

    fun initializeCourses(courses: List<UWOpenAPICourse>) {
        courses.forEachIndexed { i, it ->
            if (it.subjectCode === null || it.catalogNumber === null) {
                return@forEachIndexed
            }

            val courseCode = "${it.subjectCode} ${it.catalogNumber}"
            courseMap[courseCode] = it
        }
    }

    fun getCourse(courseCode: String): UWOpenAPICourse? {
        return courseMap[courseCode]
    }
}