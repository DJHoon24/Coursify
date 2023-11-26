package cs346.controller

import androidx.compose.runtime.getValue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NavControllerTest {

    lateinit var navController: NavController

    @Before
    fun setup() {
        navController = NavController(startDestination = "route1")
    }

    @Test
    fun navigateToChangeCurrentScreen() {
        navController.navigate("route2")

        val currentScreen: String by navController.currentScreen
        assertEquals("route2", currentScreen)
    }

    @Test
    fun navigateBackShouldChangeToPreviousScreen() {
        navController.navigate("route2")
        navController.navigateBack()

        val currentScreen: String by navController.currentScreen
        assertEquals("route1", currentScreen)
    }

    @Test
    fun navigateBackOnStartDestinationShouldDoNothing() {
        navController.navigateBack()

        val currentScreen: String by navController.currentScreen
        assertEquals("route1", currentScreen)
    }
}
