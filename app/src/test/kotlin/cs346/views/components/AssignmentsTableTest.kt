package cs346.views.components

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import cs346.model.Assignment
import cs346.model.UserTheme
import cs346.views.components.tables.AssignmentsTable
import cs346.views.theme.LocalExtendedColors
import cs346.views.theme.dateFormat
import cs346.views.theme.getExtendedColors
import cs346.views.theme.getLocalDateTime
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import org.junit.Rule
import java.util.*
import kotlin.test.Test

class AssignmentsTableTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyData = listOf(
        Assignment(
            1,
            "A1",
            dateFormat(getLocalDateTime()),
            87f,
            4f,
            3.48f,
            dateFormat(getLocalDateTime()),
            dateFormat(getLocalDateTime())
        ),
        Assignment(
            2,
            "A2",
            Clock.System.now().plus(1, DateTimeUnit.HOUR, TimeZone.UTC).toLocalDateTime(TimeZone.UTC).toString(),
            91f,
            5f,
            4.55f,
            dateFormat(getLocalDateTime()),
            dateFormat(getLocalDateTime())
        ),
    )

    @Test
    fun testAssignmentsTableIsDisplayed() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalExtendedColors provides getExtendedColors(UserTheme.Default)
            ) {
                AssignmentsTable(data = dummyData.toTypedArray(), courseId = 1)

            }
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
            assignment.dueDate.let { assignment.dueDate }.let { composeTestRule.onNodeWithText(it).assertIsDisplayed() }
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
            CompositionLocalProvider(
                LocalExtendedColors provides getExtendedColors(UserTheme.Default)
            ) {
                AssignmentsTable(data = dummyData.toTypedArray(), courseId = 1)

            }
        }

        // Click the "New" button
        composeTestRule.onNodeWithText("+").performClick()

        // Verify that a new row is added
        composeTestRule.onAllNodesWithTag(ASSIGNMENTS_TABLE_ROW_TEST_TAG)
            .assertCountEquals(dummyData.size + 3)

        // Verify that the new row contains all blank cells
        val newCells = composeTestRule.onAllNodesWithText("")
        newCells.assertCountEquals(5)

        composeTestRule.onAllNodesWithTag(ASSIGNMENT_DELETE_TEST_TAG).onLast().performClick()
        val deletedCells = composeTestRule.onAllNodesWithText("")
        deletedCells.assertCountEquals(0)
    }
}