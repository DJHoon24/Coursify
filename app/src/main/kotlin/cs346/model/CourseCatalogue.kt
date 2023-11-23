package cs346.model

import cs346.model.UWOpenAPI.UWOpenAPICourse

object CourseCatalogue {
    private val courseMap = HashMap<String, UWOpenAPICourse>()

    fun initializeCourses(courses: List<UWOpenAPICourse>) {
        courses.forEachIndexed { _, it ->
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