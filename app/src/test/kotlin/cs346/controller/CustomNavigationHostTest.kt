package cs346.controller

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import cs346.model.Course
import cs346.model.Note
import cs346.model.Screen
import cs346.model.User
import cs346.views.theme.dateFormat
import cs346.views.theme.getLocalDateTime
import org.junit.Rule
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class CustomNavigationHostTest {

    // Set up mock data
    val course1 = Course(1, "Course 1", "Course 1 description", "Course 1 start date", "Course 1 end date", rating = 1, review = "Note 1 review", notes = mutableListOf(Note(
        1, "Note 1", "Note 1 content", dateFormat(getLocalDateTime()), dateFormat(getLocalDateTime()))), createdDate = dateFormat(getLocalDateTime()), lastModifiedDate = dateFormat(getLocalDateTime()))
    val course2 = Course(2, "Course 2", "Course 2 description", "Course 2 start date", "Course 2 end date", rating = 1, review = "Note 2 review", notes = mutableListOf(Note(
        2, "Note 2", "Note 2 content", dateFormat(getLocalDateTime()), dateFormat(getLocalDateTime()))), createdDate = dateFormat(getLocalDateTime()), lastModifiedDate = dateFormat(getLocalDateTime()))

    @get:Rule
    val composeTestRule = createComposeRule()

    @BeforeTest
    fun setup() {
        User.courses.add(course1)
        User.courses.add(course2)
    }

    @AfterTest
    fun teardown() {
        // Clear mock data
        User.courses.clear()
    }

    @Test
    fun customNavigationHost_navigatesCorrectly() {
        // Instantiate the NavController with startDestination as CourseListScreen route
        val navController = NavController(startDestination = Screen.CourseListScreen.route)

        // Set up the CustomNavigationHost
        composeTestRule.setContent {
            CustomNavigationHost(navController)
        }

        // Initially, CourseListScreen should be displayed.
        composeTestRule.onNodeWithText("Course 1").assertExists()
        composeTestRule.onNodeWithText("Course 2").assertExists()

        // After navigating to CourseScreen of course1, CourseScreen should be displayed.
        composeTestRule.runOnUiThread {
            navController.navigate(Screen.CourseScreen.route.replace("{courseId}", course1.id.toString()))
        }
        composeTestRule.onNodeWithText("Course 1").assertExists()

        // After navigating to MarkdownViewer of Note 1 of course1, MarkdownViewer should be displayed.
        composeTestRule.runOnUiThread {
            val noteRoute = Screen.MarkdownScreen.route
                .replace("{courseId}", course1.id.toString())
                .replace("{noteId}", course1.notes[0].id.toString())
            navController.navigate(noteRoute)
        }
        composeTestRule.onNodeWithText("Note 1").assertExists()
    }
}
