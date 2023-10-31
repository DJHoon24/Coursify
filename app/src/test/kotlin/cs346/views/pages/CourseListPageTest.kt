package cs346.views.pages

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class CourseCardListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun addCourseButtonTest() {
        val windowState = WindowState(size = DpSize(900.dp, 900.dp))
        composeTestRule.setContent {
            CourseListPage(windowState = windowState)
        }

        // Assert that AddCourseButton is displayed initially
        composeTestRule.onNodeWithContentDescription("Add Course").assertIsDisplayed()

        // Count the number of CourseCards before clicking the button
        val initialCourseCardsCount = composeTestRule.onAllNodesWithTag("courseCardContainer").fetchSemanticsNodes().size

        // Click on AddCourseButton
        composeTestRule.onNodeWithContentDescription("Add Course").performClick()

        // Wait for recomposition
        composeTestRule.waitForIdle()

        // Assert that the courses list has increased in size by checking the number of CourseCards
        val newCourseCardsCount = composeTestRule.onAllNodesWithTag("courseCardContainer").fetchSemanticsNodes().size
        assertEquals(initialCourseCardsCount + 1, newCourseCardsCount, "The number of CourseCards did not increase by 1")
    }
}
