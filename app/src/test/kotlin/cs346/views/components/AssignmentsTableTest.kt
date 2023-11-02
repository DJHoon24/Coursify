package cs346.views.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import cs346.model.Assignment
import cs346.views.components.tables.AssignmentsTable
import cs346.views.theme.dateFormat
import org.junit.Rule
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test

class AssignmentsTableTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyData = listOf(
            Assignment(1, "A1", LocalDateTime.now(), 87f, 4f, 3.48f),
            Assignment(2, "A2", LocalDateTime.now().plusHours(1), 91f, 5f, 4.55f),
    )

    @Test
    fun testAssignmentsTableIsDisplayed() {


        composeTestRule.setContent {
            AssignmentsTable(data = dummyData.toTypedArray())
        }

        // Verify the header row is displayed
        composeTestRule.onNodeWithText("Assignment").assertIsDisplayed()
        composeTestRule.onNodeWithText("Due Date").assertIsDisplayed()
        composeTestRule.onNodeWithText("Score").assertIsDisplayed()
        composeTestRule.onNodeWithText("Weight (%)").assertIsDisplayed()
        composeTestRule.onNodeWithText("Weighted Mark").assertIsDisplayed()

        // Verify the number of rows in the table (data rows + header and new row button)
        val rowNodes = composeTestRule.onAllNodesWithTag(ASSIGNMENTS_TABLE_ROW_TEST_TAG)
        rowNodes.assertCountEquals(dummyData.size + 2)

        // Verify cells contain the provided data
        dummyData.forEachIndexed { _, assignment ->
            composeTestRule.onNodeWithText(assignment.name).assertIsDisplayed()
            composeTestRule.onNodeWithText(dateFormat.format(assignment.dueDate)).assertIsDisplayed()
            composeTestRule.onNodeWithText(assignment.score.toString()).assertIsDisplayed()
            composeTestRule.onNodeWithText(assignment.weight.toString()).assertIsDisplayed()
            composeTestRule.onNodeWithText(assignment.weightedMark.toString()).assertIsDisplayed()
        }

        // Verify the total row is displayed
        composeTestRule.onNodeWithText(String.format(Locale.US, "%.2f", 8.03)).assertIsDisplayed()
    }

    @Test
    fun testNewButtonCreatesNewTableRow() {

        composeTestRule.setContent {
            AssignmentsTable(data = dummyData.toTypedArray())
        }

        // Click the "New" button
        composeTestRule.onNodeWithText("+").performClick()

        // Verify that a new row is added
        composeTestRule.onAllNodesWithTag(ASSIGNMENTS_TABLE_ROW_TEST_TAG)
                .assertCountEquals(dummyData.size + 3)

        // Verify that the new row contains all blank cells
        val newCells = composeTestRule.onAllNodesWithText("")
        newCells.assertCountEquals(5)
    }
}