package cs346.controller

import cs346.model.CourseCatalogue
import cs346.model.UWOpenAPI.UWOpenAPICourse
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class UWOpenAPIControllerTest {
    private lateinit var expectedCourses: List<UWOpenAPICourse>

    @Before
    fun setUp() {
        expectedCourses = listOf(
            UWOpenAPICourse(
                courseId = "004392",
                courseOfferNumber = 1,
                termCode = "1239",
                termName = "Fall 2023",
                associatedAcademicCareer = "UG",
                associatedAcademicGroupCode = "MAT",
                associatedAcademicOrgCode = "CS",
                subjectCode = "CS",
                catalogNumber = "341",
                title = "Algorithms",
                descriptionAbbreviated = "Algorithms",
                description = "The study of efficient algorithms and effective algorithm design techniques. Program design with emphasis on pragmatic and mathematical aspects of program efficiency. Topics include divide and conquer algorithms, recurrences, greedy algorithms, dynamic programming, graph search and backtrack, problems without algorithms, NP-completeness and its implications. [Note: Enrolment is restricted; see Note 1 above. Lab is not scheduled and students are expected to find time in open hours to complete their work.]",
                gradingBasis = "NUM",
                courseComponentCode = "LEC",
                enrollConsentCode = "N",
                enrollConsentDescription = "No Consent Required",
                dropConsentCode = "N",
                dropConsentDescription = "No Consent Required",
                requirementsDescription = "Prereq: CS 240/240E and (MATH 239 or 249); Computer Science and BMath (Data Science) students only. Antireq: CS 231, ECE 406"
            ),
            UWOpenAPICourse(
                courseId = "016287",
                courseOfferNumber = 1,
                termCode = "1239",
                termName = "Fall 2023",
                associatedAcademicCareer = "UG",
                associatedAcademicGroupCode = "MAT",
                associatedAcademicOrgCode = "CS",
                subjectCode = "CS",
                catalogNumber = "346",
                title = "Application Development",
                descriptionAbbreviated = "Application Development",
                description = "Introduction to full-stack application design and development. Students will work in project teams to design and build complete, working applications and services using standard tools. Topics include best-practices in design, development, testing, and deployment.",
                gradingBasis = "NUM",
                courseComponentCode = "LEC",
                enrollConsentCode = "N",
                enrollConsentDescription = "No Consent Required",
                dropConsentCode = "N",
                dropConsentDescription = "No Consent Required",
                requirementsDescription = "Prereq: CS 246/246E; Computer Science students only"
            )
        )
    }

    @Test
    fun testPopulateTermCourseData() {
        var result = false
        UWOpenAPIController.callRequest {
            result = UWOpenAPIController.populateTermCourseData()
        }

        assertEquals(result, true)

        val courseCS346 = CourseCatalogue.getCourse("CS 346")
        val courseCS341 = CourseCatalogue.getCourse("CS 341")

        assertEquals(courseCS341?.description, expectedCourses[0].description)
        assertEquals(courseCS346?.description, expectedCourses[1].description)
    }

    @Test
    fun testGetCourseSchedule() {
        var result = ""
        UWOpenAPIController.callRequest {
            result = UWOpenAPIController.getCourseSchedule(courseId = expectedCourses[1].courseId!!)
        }

        assertEquals(result, "W 10:30 - 12:20")
    }
}