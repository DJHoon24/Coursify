package cs346.views.pages

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun emailAndPasswordFieldsExist() {
        composeTestRule.setContent {
            LoginScreen {}
        }

        composeTestRule.onNodeWithTag("emailField").assertExists()
        composeTestRule.onNodeWithTag("passwordField").assertExists()
        composeTestRule.onNodeWithTag("loginButton").assertExists()
        composeTestRule.onNodeWithTag("switchButton").assertExists()
    }

    @Test
    fun signInMode_Login() {
        composeTestRule.setContent {
            LoginScreen {}
        }

        composeTestRule.onNodeWithTag("loginButton").assertTextEquals("Login")
        composeTestRule.onNodeWithTag("switchButton").assertTextEquals("Switch to Sign Up")
    }

    @Test
    fun signInMode_SignUp() {
        composeTestRule.setContent {
            LoginScreen {}
        }

        // Simulate switching the sign-in mode to Sign Up
        composeTestRule.onNodeWithTag("switchButton").performClick()

        composeTestRule.onNodeWithTag("emailField").assertExists()
        composeTestRule.onNodeWithTag("passwordField").assertExists()
        composeTestRule.onNodeWithTag("firstNameField").assertExists()
        composeTestRule.onNodeWithTag("lastNameField").assertExists()
        composeTestRule.onNodeWithTag("loginButton").assertTextEquals("Sign Up")
        composeTestRule.onNodeWithTag("switchButton").assertTextEquals("Switch to Login")
    }

    @Test
    fun firstNameAndLastNameFieldsNotInLoginMode() {
        composeTestRule.setContent {
            LoginScreen {}
        }

        composeTestRule.onNodeWithTag("firstNameField").assertDoesNotExist()
        composeTestRule.onNodeWithTag("lastNameField").assertDoesNotExist()
    }
}
