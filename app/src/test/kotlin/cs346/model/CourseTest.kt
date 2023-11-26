package cs346.model

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class CourseTest {
    private lateinit var courseList: MutableList<Course>

    @BeforeTest
    fun setUp() {
        courseList = mutableListOf()
    }

    @AfterTest
    fun tearDown() {
        courseList.clear()
    }

    @Test
    fun testAddCourses() {
        courseList.add(
            "CS 001",
            "Wed, Fri | 2:30 PM - 4:20 PM | MC 2035",
            "Jeff Avery",
            "Fantastic course",
            "Nice prof",
            4
        )
        assertEquals(1, courseList.size)
        val course = courseList[0]
        assertEquals("CS 001", course.courseNumber)
        assertEquals("Wed, Fri | 2:30 PM - 4:20 PM | MC 2035", course.lectureInfo)
        assertEquals("Jeff Avery", course.instructors)
        assertEquals("Fantastic course", course.courseDescription)
        assertEquals("Nice prof", course.review)
        assertEquals(4, course.rating)
        assertNotNull(course.createdDate)
        assertNotNull(course.lastModifiedDate)
    }

    @Test
    fun testEditCourses() {
        courseList.add(
            "CS 001",
            "Wed, Fri | 2:30 PM - 4:20 PM | MC 2035",
            "Jeff Avery",
            "Fantastic course",
            "Nice prof",
            4
        )
        val originalCourse = courseList[0]
        val originalID = originalCourse.id
        courseList.editCourseNumber("CS 002", originalID)
        courseList.editLectureInfo("great info", originalID)
        courseList.editInstructors("Feridun", originalID)
        courseList.editCourseDescription("cool description", originalID)
        courseList.editReview("cool prof", originalID)
        courseList.editRating(3, originalID)

        assertEquals(1, courseList.size)
        val updatedCourse = courseList[0]
        assertEquals("CS 002", updatedCourse.courseNumber)
        assertEquals("great info", updatedCourse.lectureInfo)
        assertEquals("Feridun", updatedCourse.instructors)
        assertEquals("cool description", updatedCourse.courseDescription)
        assertEquals("cool prof", updatedCourse.review)
        assertEquals(3, updatedCourse.rating)
    }

    @Test
    fun testGetById() {
        courseList.add(
            "CS 001",
            "Wed, Fri | 2:30 PM - 4:20 PM | MC 2035",
            "Jeff Avery",
            "Fantastic course",
            "Nice prof",
            4
        )
        val originalCourse = courseList[0]
        val originalID = originalCourse.id
        val course = courseList.getById(originalID)
        assertNotNull(course)
        assertEquals(originalCourse, course)
    }

    @Test
    fun testGetById_NotFound() {
        val course = courseList.getById(-1)
        assertNull(course)
    }
}