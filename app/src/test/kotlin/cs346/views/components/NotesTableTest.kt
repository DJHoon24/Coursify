package cs346.views.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import cs346.controller.NavController
import cs346.model.Note
import cs346.views.components.tables.NotesTable
import cs346.views.theme.dateFormat
import org.junit.Rule
import java.time.LocalDateTime
import kotlin.test.Test

class NotesTableTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyData = listOf(
            Note(1,  "Note 1", createdDateTime = LocalDateTime.now(), lastModifiedDateTime = LocalDateTime.now().plusHours(1)),
            Note(2, "Note 2", createdDateTime = LocalDateTime.now().plusHours(2), lastModifiedDateTime = LocalDateTime.now().plusHours(3)),
    )

    @Test
    fun testNotesTableIsDisplayed() {


        composeTestRule.setContent {
            NotesTable(data = dummyData.toTypedArray(), courseId = 1, navController = NavController("test"))
        }

        // Verify the header row is displayed
        composeTestRule.onNodeWithText("Note").assertIsDisplayed()
        composeTestRule.onNodeWithText("Modified").assertIsDisplayed()
        composeTestRule.onNodeWithText("Created").assertIsDisplayed()

        // Verify the number of rows in the table (data rows + header and new row button)
        val rowNodes = composeTestRule.onAllNodesWithTag(NOTES_TABLE_ROW_TEST_TAG)
        rowNodes.assertCountEquals(dummyData.size + 2)

        // Verify cells contain the provided data
        dummyData.forEachIndexed { _, note ->
            composeTestRule.onNodeWithText(note.title).assertIsDisplayed()
            composeTestRule.onNodeWithText(dateFormat.format(note.lastModifiedDateTime)).assertIsDisplayed()
            composeTestRule.onNodeWithText(dateFormat.format(note.createdDateTime)).assertIsDisplayed()
        }
    }

    @Test
    fun testNewButtonCreatesNewTableRow() {

        composeTestRule.setContent {
            NotesTable(data = dummyData.toTypedArray(), courseId = 1, navController = NavController("test"))
        }

        // Click the "New" button
        composeTestRule.onNodeWithText("+").performClick()

        // Verify that a new row is added
        composeTestRule.onAllNodesWithTag(NOTES_TABLE_ROW_TEST_TAG)
                .assertCountEquals(dummyData.size + 3)

        // Verify that the new row contains a single blank cell
        val newCells = composeTestRule.onAllNodesWithText("")
        newCells.assertCountEquals(1)
    }
}