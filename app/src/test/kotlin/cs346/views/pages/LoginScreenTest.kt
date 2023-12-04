package cs346.views.pages

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import cs346.model.UserTheme
import cs346.views.theme.LocalExtendedColors
import cs346.views.theme.getExtendedColors
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun emailAndPasswordFieldsExist() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalExtendedColors provides getExtendedColors(UserTheme.Default)
            ) {
                LoginScreen {}
            }
        }

        composeTestRule.onNodeWithTag("emailField").assertExists()
        composeTestRule.onNodeWithTag("passwordField").assertExists()
        composeTestRule.onNodeWithTag("loginButton").assertExists()
        composeTestRule.onNodeWithTag("switchButton").assertExists()
    }

    @Test
    fun signInMode_Login() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalExtendedColors provides getExtendedColors(UserTheme.Default)
            ) {
                LoginScreen {}
            }
        }

        composeTestRule.onNodeWithTag("loginButton").assertTextEquals("Login")
        composeTestRule.onNodeWithTag("switchButton").assertTextEquals("Switch to Sign Up")
    }

    @Test
    fun signInMode_SignUp() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalExtendedColors provides getExtendedColors(UserTheme.Default)
            ) {
                LoginScreen {}
            }
        }

        // Simulate switching the sign-in mode to Sign Up
        composeTestRule.onNodeWithTag("switchButton").performClick()

        composeTestRule.onNodeWithTag("emailField").assertExists()
        composeTestRule.onNodeWithTag("passwordField").assertExists()
        composeTestRule.onNodeWithTag("confirmPasswordField").assertExists()
        composeTestRule.onNodeWithTag("firstNameField").assertExists()
        composeTestRule.onNodeWithTag("lastNameField").assertExists()
        composeTestRule.onNodeWithTag("loginButton").assertTextEquals("Sign Up")
        composeTestRule.onNodeWithTag("switchButton").assertTextEquals("Switch to Login")
    }

    @Test
    fun firstNameAndLastNameFieldsNotInLoginMode() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalExtendedColors provides getExtendedColors(UserTheme.Default)
            ) {
                LoginScreen {}
            }
        }

        composeTestRule.onNodeWithTag("confirmPasswordField").assertDoesNotExist()
        composeTestRule.onNodeWithTag("firstNameField").assertDoesNotExist()
        composeTestRule.onNodeWithTag("lastNameField").assertDoesNotExist()
    }
}
