package cs346.controller

import androidx.compose.material.Text
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class NavigationHostTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun navigationShouldRouteToTheDestinationsCorrectly() {
        // Instantiate the NavController with startDestination as "route1"
        val navController = NavController(startDestination = "route1")

        // Set up the NavigationHost with two routes.
        val navigationHost = NavigationHost(
            navController = navController,
            contents = {
                composable("route1") {
                    Text("Route 1")
                }
                composable("route2") {
                    Text("Route 2")
                }
            }
        )

        // Initially, "Route 1" should be displayed.
        composeTestRule.setContent { navigationHost.build() }
        composeTestRule.onNodeWithText("Route 1").assertExists()
        composeTestRule.onNodeWithText("Route 2").assertDoesNotExist()

        // After navigating to "route2", "Route 2" should be displayed.
        composeTestRule.runOnUiThread {
            navController.navigate("route2")
        }

        composeTestRule.onNodeWithText("Route 1").assertDoesNotExist()
        composeTestRule.onNodeWithText("Route 2").assertExists()
    }
}
