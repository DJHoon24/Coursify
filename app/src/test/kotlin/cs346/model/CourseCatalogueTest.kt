package cs346.model

import cs346.model.UWOpenAPI.UWOpenAPICourse
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CourseCatalogueTest {

    @BeforeTest
    fun setUp() {
        CourseCatalogue.initializeCourses(listOf(
            UWOpenAPICourse("CS101", 1, "2023", "Fall", "UG", "CS", "CS", "CS", "101", "Intro to CS", "Intro", "Introduction to Computer Science", "Standard", "LEC", "None", "No consent required", "None", "No consent required", "None"),
            UWOpenAPICourse("CS102", 1, "2023", "Fall", "UG", "CS", "CS", "CS", "102", "Data Structures", "Data", "Data Structures and Algorithms", "Standard", "LEC", "None", "No consent required", "None", "No consent required", "None")
        ))
    }

    @Test
    fun testGetCourse() {
        val course = CourseCatalogue.getCourse("CS 101")
        assertEquals("CS 101", "${course?.subjectCode} ${course?.catalogNumber}")
        assertEquals("Intro to CS", course?.title)
        assertEquals("Introduction to Computer Science", course?.description)

        val course2 = CourseCatalogue.getCourse("CS 102")
        assertEquals("CS 102", "${course2?.subjectCode} ${course2?.catalogNumber}")
        assertEquals("Data Structures", course2?.title)
        assertEquals("Data Structures and Algorithms", course2?.description)
    }

    @Test
    fun testGetCourse_NotFound() {
        val course = CourseCatalogue.getCourse("CS -1")
        assertNull(course)
    }
}
