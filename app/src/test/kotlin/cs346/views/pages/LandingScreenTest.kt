package cs346.views.pages

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class LandingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun landingScreen_isDisplayed() {
        composeTestRule.setContent {
            LandingScreen {}
        }

        // Check if the texts are displayed.
        composeTestRule.onNodeWithText("Coursify").assertIsDisplayed()
        composeTestRule.onNodeWithText("The all-in-one note taking\napp to help you take\ncontrol of your semester").assertIsDisplayed()
        composeTestRule.onNodeWithText("Get Started").assertIsDisplayed()
    }

    @Test
    fun landingScreen_navigateOnButtonClick() {
        var navigateCalled = false
        composeTestRule.setContent {
            LandingScreen { navigateCalled = true }
        }

        // Perform a click on the "Get Started" button.
        composeTestRule.onNodeWithText("Get Started").performClick()

        // Check if the navigation function is called.
        assert(navigateCalled)
    }
}
