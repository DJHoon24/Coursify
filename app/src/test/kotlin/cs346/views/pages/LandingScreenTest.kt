package cs346.views.pages

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import cs346.model.UserTheme
import cs346.views.theme.LocalExtendedColors
import cs346.views.theme.getExtendedColors
import org.junit.Rule
import org.junit.Test

class LandingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun landingScreen_isDisplayed() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalExtendedColors provides getExtendedColors(UserTheme.Default)
            ) {
                LandingScreen {}
            }
        }

        // Check if the texts are displayed.
        composeTestRule.onNodeWithText("Coursify").assertIsDisplayed()
        composeTestRule.onNodeWithText("The all-in-one note taking\napp to help you take\ncontrol of your semester")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Get Started").assertIsDisplayed()
    }

    @Test
    fun landingScreen_navigateOnButtonClick() {
        var navigateCalled = false
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalExtendedColors provides getExtendedColors(UserTheme.Default)
            ) {
                LandingScreen { navigateCalled = true }
            }
        }

        // Perform a click on the "Get Started" button.
        composeTestRule.onNodeWithText("Get Started").performClick()

        // Check if the navigation function is called.
        assert(navigateCalled)
    }
}
