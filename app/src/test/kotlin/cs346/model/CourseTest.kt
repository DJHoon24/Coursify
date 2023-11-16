package cs346.model

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

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
        courseList.add(
            "CS 002",
            "Tue, Thu | 2:30 PM - 4:20 PM | MC 1012",
            "Feridun",
            "Amazing yay",
            "hooray i love this prof",
            5
        )
        assertEquals(2, courseList.size)
        assertEquals("CS 001", courseList[0].courseNumber)
        assertEquals("Wed, Fri | 2:30 PM - 4:20 PM | MC 2035", courseList[0].lectureInfo)
        assertEquals("Jeff Avery", courseList[0].instructors)
        assertEquals("Fantastic course", courseList[0].courseDescription)
        assertEquals("Nice prof", courseList[0].review)
        assertEquals(4, courseList[0].rating)
        assertEquals("CS 002", courseList[1].courseNumber)
        assertEquals("Tue, Thu | 2:30 PM - 4:20 PM | MC 1012", courseList[1].lectureInfo)
        assertEquals("Feridun", courseList[1].instructors)
        assertEquals("Amazing yay", courseList[1].courseDescription)
        assertEquals("hooray i love this prof", courseList[1].review)
        assertEquals(5, courseList[1].rating)
    }

//    @Test
//    fun testEditCourses() {
//        courseList.add(
//            "CS 001",
//            "Wed, Fri | 2:30 PM - 4:20 PM | MC 2035",
//            "Jeff Avery",
//            "Fantastic course",
//            "Nice prof",
//            4
//        )
//        val originalID = courseList[0].id
//        val originalModifiedDate = courseList[0].lastModifiedDate
//        Thread.sleep(500)
//        courseList.editCourseNumber("CS 002", originalID)
//        courseList.editLectureInfo("great info", originalID)
//        courseList.editInstructors("Feridun", originalID)
//        courseList.editCourseDescription("cool description", originalID)
//        courseList.editReview("cool prof", originalID)
//        courseList.editRating(3, originalID)
//        assertEquals(1, courseList.size)
//        assertEquals("CS 002", courseList[0].courseNumber)
//        assertEquals("great info", courseList[0].lectureInfo)
//        assertEquals("Feridun", courseList[0].instructors)
//        assertEquals("cool description", courseList[0].courseDescription)
//        assertEquals("cool prof", courseList[0].review)
//        assertEquals(3, courseList[0].rating)
//        assertNotEquals(originalModifiedDate, courseList[0].lastModifiedDate)
//    }
}