package cs346.views.pages

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import cs346.controller.NavController
import cs346.model.Note
import cs346.views.components.MARKDOWN_EDIT_TEST_TAG
import cs346.views.components.MARKDOWN_VIEW_TEST_TAG
import cs346.views.components.NOTE_TITLE_TEST_TAG
import cs346.views.theme.dateFormat
import cs346.views.theme.getLocalDateTime
import org.junit.Rule
import org.junit.Test

class MarkDownNotePageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun presetNoteTest() {
        composeTestRule.setContent {
            MarkdownViewer(
                NavController("test"),
                Note(
                    1,
                    "PRESET TITLE",
                    "preset content",
                    dateFormat(getLocalDateTime()),
                    dateFormat(getLocalDateTime())
                )
            )
        }

        val titleNode = composeTestRule.onNodeWithTag(NOTE_TITLE_TEST_TAG)
        titleNode.assertIsDisplayed()
        titleNode.assertTextEquals("PRESET TITLE")

        val editMarkdownNode = composeTestRule.onNodeWithTag(MARKDOWN_EDIT_TEST_TAG)
        editMarkdownNode.assertIsDisplayed()
        editMarkdownNode.assertTextEquals("preset content")

        val viewMarkdownNode = composeTestRule.onNodeWithTag(MARKDOWN_VIEW_TEST_TAG)
        viewMarkdownNode.assertIsDisplayed()
    }

    @Test
    fun newNoteTest() {
        composeTestRule.setContent {
            MarkdownViewer(NavController("test"))
        }

        val titleNode = composeTestRule.onNodeWithTag(NOTE_TITLE_TEST_TAG)
        titleNode.assertIsDisplayed()

        val editMarkdownNode = composeTestRule.onNodeWithTag(MARKDOWN_EDIT_TEST_TAG)
        editMarkdownNode.assertIsDisplayed()
        editMarkdownNode.assertTextEquals("")

        val viewMarkdownNode = composeTestRule.onNodeWithTag(MARKDOWN_VIEW_TEST_TAG)
        viewMarkdownNode.assertIsDisplayed()

        titleNode.performTextInput("New Title to New Note")
        titleNode.assertTextEquals("New Title to New Note")

        editMarkdownNode.performTextInput("New content to this")
        editMarkdownNode.assertTextEquals("New content to this")
    }
}