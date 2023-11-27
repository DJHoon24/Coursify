package cs346.views.sidebar

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import cs346.controller.NavController
import cs346.model.Course
import cs346.model.User
import cs346.model.UserTheme
import cs346.views.theme.LocalExtendedColors
import cs346.views.theme.dateFormat
import cs346.views.theme.getExtendedColors
import cs346.views.theme.getLocalDateTime
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SidebarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: NavController

    @Before
    fun setUp() {
        User.firstName = "Test"
        User.lastName = "User"
        User.email = "test@example.com"
        User.courses = mutableStateListOf(
            Course(
                id = 1,
                courseNumber = "CS101",
                lectureInfo = "MWF 10:30-11:20",
                instructors = "Jeff Avery",
                courseDescription = "Intro to CS",
                review = "Great course",
                rating = 5,
                mutableListOf(),
                mutableListOf(),
                dateFormat(getLocalDateTime()),
                dateFormat(getLocalDateTime())
            )
        )
        navController = NavController("CourseList")
    }

    @Test
    fun testSidebarRendersCorrectly() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalExtendedColors provides getExtendedColors(UserTheme.Default)
            ) {
                Sidebar(navController = navController, onNavigate = {})

            }
        }

        composeTestRule.onNodeWithText("Test User").assertExists()
        composeTestRule.onNodeWithText("Courses").assertExists()
        composeTestRule.onNodeWithText("CS101").assertExists()
        composeTestRule.onNodeWithText("Sign out").assertExists()
    }

    @Test
    fun testCourseItemClickNavigatesToCourseScreen() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalExtendedColors provides getExtendedColors(UserTheme.Default)
            ) {
                Sidebar(navController = navController, onNavigate = {})

            }
        }

        composeTestRule.onNodeWithText("CS101").performClick()

        assert(navController.currentScreen.value == "Course/1")
    }

    @Test
    fun testSignOutButtonClearsUserState() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalExtendedColors provides getExtendedColors(UserTheme.Default)
            ) {
                Sidebar(navController = navController, onNavigate = {})

            }
        }

        composeTestRule.onNodeWithText("Sign out").performClick()

        assert(User.firstName == "")
        assert(User.lastName == "")
        assert(User.email == "")
        assert(User.courses.isEmpty())
    }
}
