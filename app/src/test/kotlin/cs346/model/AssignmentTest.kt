package cs346.model

import java.time.LocalDateTime
import kotlin.test.*

class AssignmentTest {
    private lateinit var assignmentList: MutableList<Assignment>

    @BeforeTest
    fun setUp() {
        assignmentList = mutableListOf()
    }

    @AfterTest
    fun tearDown() {
        assignmentList.clear()
    }

    @Test
    fun testAddCourses() {
        assignmentList.add("A1", LocalDateTime.parse("2023-12-14T09:55:00"), 85.0f, 4.0f)
        assignmentList.add("A2", LocalDateTime.parse("2023-12-15T09:55:00"), 77.0f, 10.0f)
        assertEquals(2, assignmentList.size)
        assertEquals("A1", assignmentList[0].name)
        assertEquals(LocalDateTime.parse("2023-12-14T09:55:00"), assignmentList[0].dueDate)
        assertEquals(85.0f, assignmentList[0].score)
        assertEquals(4.0f, assignmentList[0].weight)
        assertEquals(3.4f, assignmentList[0].weightedMark)
        assertEquals(1, assignmentList[0].id)
        assertEquals("A2", assignmentList[1].name)
        assertEquals(LocalDateTime.parse("2023-12-15T09:55:00"), assignmentList[1].dueDate)
        assertEquals(77.0f, assignmentList[1].score)
        assertEquals(10.0f, assignmentList[1].weight)
        assertEquals(7.7f, assignmentList[1].weightedMark)
        assertEquals(2, assignmentList[1].id)
    }

    @Test
    fun testEditCourses() {
        assignmentList.add("A1", LocalDateTime.parse("2023-12-14T09:55:00"), 85.0f, 4.0f)
        val originalID = assignmentList[0].id
        val originalModifiedDate = assignmentList[0].lastModifiedDate
        Thread.sleep(500)
        assignmentList.editName("A2", originalID)
        assignmentList.editDueDate(LocalDateTime.parse("2023-12-15T09:55:00"), originalID)
        assignmentList.editScore(77.0f, originalID)
        assignmentList.editWeight(10.0f, originalID)
        assertEquals(1, assignmentList.size)
        assertEquals("A2", assignmentList[0].name)
        assertEquals(LocalDateTime.parse("2023-12-15T09:55:00"), assignmentList[0].dueDate)
        assertEquals(77.0f, assignmentList[0].score)
        assertEquals(10.0f, assignmentList[0].weight)
        assertEquals(7.7f, assignmentList[0].weightedMark)
        assertNotEquals(originalModifiedDate, assignmentList[0].lastModifiedDate)
    }
}