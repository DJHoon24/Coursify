package cs346.views.pages

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class CourseScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testExpandableTextField() {
        val title = "Description"
        val content = mutableStateOf("Sample Description")

        composeTestRule.setContent {
            ExpandableTextField(title = title, content = content)
        }

        // Assert description is initially hidden
        composeTestRule.onNodeWithText(content.value).assertDoesNotExist()

        // Assert description is visible after clicking on arrow button
        composeTestRule.onNodeWithContentDescription("Expand").performClick()
        composeTestRule.onNodeWithText(content.value).assertIsDisplayed()
    }

    @Test
    fun testStarRating() {
        val rating = mutableStateOf(4)

        composeTestRule.setContent {
            StarRating(rating = rating)
        }

        // Verify clicking on the 5th star sets the rating to 5
        composeTestRule.onNodeWithContentDescription("Unselected").performClick()
        assert(rating.value == 5)
    }
}
