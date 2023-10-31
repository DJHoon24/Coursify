package cs346.views.components

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import cs346.views.pages.CourseCardData
import org.junit.Rule
import kotlin.test.Test


class CourseCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testStaticCourseCardRendersText() {
        val testData = CourseCardData(
            courseCode = mutableStateOf("CS 346"),
            schedule = mutableStateOf("Mon, Wed, Fri")
        )

        // Set editable to false and provide the course code and schedule
        composeTestRule.setContent {
            CourseCard(courseCardData = testData)
        }

        // Assert the component displays non-editable text
        composeTestRule.onNodeWithText("CS 346").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mon, Wed, Fri").assertIsDisplayed()

    }

    @Test
    fun testEditableCourseCardRendersTextFields() {
        val testData = CourseCardData(
            courseCode = mutableStateOf("CS 346"),
            schedule = mutableStateOf("Mon, Wed, Fri")
        )

        composeTestRule.setContent {
            CourseCard(courseCardData = testData)
        }

        // Assert the component renders editable text fields with initial text
        val courseCodeNode = composeTestRule.onNodeWithTag(COURSE_CODE_TEST_TAG)
        courseCodeNode.assertTextEquals("CS 346")

        val scheduleNode = composeTestRule.onNodeWithTag(COURSE_SCHEDULE_TEST_TAG)
        scheduleNode.assertTextEquals("Mon, Wed, Fri")

        // Perform text input and assert the fields are updated
        courseCodeNode.performTextClearance()
        courseCodeNode.performTextInput("Updated CS 346")
        courseCodeNode.assertTextEquals("Updated CS 346")

        scheduleNode.performTextClearance()
        scheduleNode.performTextInput("Updated Mon, Wed, Fri")
        scheduleNode.assertTextEquals("Updated Mon, Wed, Fri")
    }

}