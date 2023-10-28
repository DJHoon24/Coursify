package cs346.views.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class AddCourseButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun addCourseButtonIsClicked() {
        var clicked = false

        composeTestRule.setContent {
            AddCourseButton(onClick = { clicked = true })
        }

        // Click on the AddCourseButton
        composeTestRule.onNodeWithContentDescription("Add Course").performClick()
        assert(clicked)
    }

    @Test
    fun addCourseButtonIconIsDisplayed() {
        composeTestRule.setContent {
            AddCourseButton(onClick = {})
        }

        // Assert the Add Course icon is displayed
        composeTestRule.onNodeWithContentDescription("Add Course").assertIsDisplayed()
    }
}
