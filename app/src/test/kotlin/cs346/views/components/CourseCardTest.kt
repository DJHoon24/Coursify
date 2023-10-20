package cs346.views.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import kotlin.test.Test


class CourseCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testStaticCourseCardRendersText() {
        // Set editable to false and provide the course code and schedule
        composeTestRule.setContent {
            CourseCard(editable = false, courseCode = "CS 346", schedule = "Mon, Wed, Fri")
        }

        // Assert the component displays non-editable text
        composeTestRule.onNodeWithText("CS 346").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mon, Wed, Fri").assertIsDisplayed()

    }

    @Test
    fun testEditableCourseCardRendersTextFields() {
        // Set editable to false and provide the course code and schedule
        composeTestRule.setContent {
            CourseCard(editable = true, courseCode = "CS 346", schedule = "Mon, Wed, Fri")
        }

        // Assert the component does not render static text
        composeTestRule.onNodeWithText("CS 346").assertDoesNotExist()
        composeTestRule.onNodeWithText("Mon, Wed, Fri").assertDoesNotExist()

        // Assert the component renders editable text fields
        val courseCodeNode = composeTestRule.onNodeWithTag(COURSE_CODE_TEST_TAG)
        courseCodeNode.performTextInput("CS 346")
        courseCodeNode.assert(hasText("CS 346"))

        val scheduleNode = composeTestRule.onNodeWithTag(COURSE_SCHEDULE_TEST_TAG)
        scheduleNode.performTextInput("Mon, Wed, Fri")
        scheduleNode.assert(hasText("Mon, Wed, Fri"))
    }

}