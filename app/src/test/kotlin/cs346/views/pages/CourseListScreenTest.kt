package cs346.views.pages

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import cs346.controller.NavController
import cs346.model.UserTheme
import cs346.views.theme.LocalExtendedColors
import cs346.views.theme.getExtendedColors
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class CourseCardListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun addCourseButtonTest() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalExtendedColors provides getExtendedColors(UserTheme.Default)
            ) {
                CourseListScreen(NavController("test"))
            }
        }

        // Assert that AddCourseButton is displayed initially
        composeTestRule.onNodeWithContentDescription("Add Course").assertIsDisplayed()

        // Count the number of CourseCards before clicking the button
        val initialCourseCardsCount =
            composeTestRule.onAllNodesWithTag("courseCardContainer").fetchSemanticsNodes().size

        // Click on AddCourseButton
        composeTestRule.onNodeWithContentDescription("Add Course").performClick()

        // Wait for recomposition
        composeTestRule.waitForIdle()

        // Assert that the courses list has not increased in size by checking the number of CourseCards
        val newCourseCardsCount = composeTestRule.onAllNodesWithTag("courseCardContainer").fetchSemanticsNodes().size
        assertEquals(initialCourseCardsCount, newCourseCardsCount, "The number of CourseCards did not increase by 1")
    }
}
