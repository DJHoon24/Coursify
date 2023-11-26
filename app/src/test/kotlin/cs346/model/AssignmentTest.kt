package cs346.model

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

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
    fun testAddAssignments() {
        assignmentList.add("A1", "2023-12-14T09:55:00", 85.0f, 4.0f)
        assignmentList.add("A2", "2023-12-15T09:55:00", 77.0f, 10.0f)
        assertEquals(2, assignmentList.size)
        val assignment1 = assignmentList[0]
        assertEquals("A1", assignment1.name)
        assertEquals("2023-12-14T09:55:00", assignment1.dueDate)
        assertEquals(85.0f, assignment1.score)
        assertEquals(4.0f, assignment1.weight)
        assertEquals(3.4f, assignment1.weightedMark)
        assertNotNull(assignment1.createdDate)
        assertNotNull(assignment1.lastModifiedDate)

        val assignment2 = assignmentList[1]
        assertEquals("A2", assignment2.name)
        assertEquals("2023-12-15T09:55:00", assignment2.dueDate)
        assertEquals(77.0f, assignment2.score)
        assertEquals(10.0f, assignment2.weight)
        assertEquals(7.7f, assignment2.weightedMark)
        assertNotNull(assignment2.createdDate)
        assertNotNull(assignment2.lastModifiedDate)
    }

    @Test
    fun testEditAssignments() {
        assignmentList.add("A3", "2023-12-14T09:55:00", 85.0f, 4.0f)
        val originalAssignment = assignmentList[0]
        val originalID = originalAssignment.id
        assignmentList.editName("A2", originalID)
        assignmentList.editDueDate("2023-12-15T09:55:00", originalID)
        assignmentList.editScore(77.0f, originalID)
        assignmentList.editWeight(10.0f, originalID)

        assertEquals(1, assignmentList.size)
        val updatedAssignment = assignmentList[0]
        assertEquals("A2", updatedAssignment.name)
        assertEquals("2023-12-15T09:55:00", updatedAssignment.dueDate)
        assertEquals(77.0f, updatedAssignment.score)
        assertEquals(10.0f, updatedAssignment.weight)
        assertEquals(7.7f, updatedAssignment.weightedMark)
    }

    @Test
    fun testGetById() {
        assignmentList.add("A1", "2023-12-14T09:55:00", 85.0f, 4.0f)
        val originalAssignment = assignmentList[0]
        val originalID = originalAssignment.id
        val assignment = assignmentList.getById(originalID)
        assertNotNull(assignment)
        assertEquals(originalAssignment, assignment)
    }

    @Test
    fun testGetById_NotFound() {
        val assignment = assignmentList.getById(999)
        assertNull(assignment)
    }
}