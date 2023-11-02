package cs346

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import cs346.controller.CustomNavigationHost
import cs346.controller.FileUserPreferencesController
import cs346.controller.UserPreferencesController
import cs346.model.Note
import cs346.model.UserPreferences
import cs346.views.pages.MarkdownViewer
import cs346.views.pages.CourseListScreen
import cs346.views.pages.LandingScreen
import cs346.controller.rememberNavController
import cs346.model.Screen
import cs346.views.sidebar.Sidebar
import java.io.File

@Composable
fun App() {
    val navController by rememberNavController(Screen.LandingScreen.route)

    Row {
        Sidebar(navController)
        Box(modifier = Modifier.fillMaxSize()) {
            CustomNavigationHost(navController)
        }
    }
}

fun main() = application {
    // TODO: save the user preferences in db instead of local file
    val userHome = System.getProperty("user.home")
    val desktopDirectory = "$userHome/Desktop"
    val preferencesController = FileUserPreferencesController(File(desktopDirectory, "user-preferences.txt"))
    val windowState = rememberWindowState()

    ManageUserPreferences(preferencesController, windowState)
    Window(
        title = "Coursify",
        state = windowState,
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}

@Composable
fun ManageUserPreferences(controller: UserPreferencesController, windowState: WindowState) {
    val userPreferences = remember { mutableStateOf(controller.loadPreferences()) }
    windowState.apply {
        size = DpSize(userPreferences.value.windowWidth, userPreferences.value.windowHeight)
        placement = userPreferences.value.placement
        isMinimized = userPreferences.value.isMinimized
        position = WindowPosition(userPreferences.value.positionX, userPreferences.value.positionY)
    }

    DisposableEffect(windowState) {
        onDispose {
            controller.savePreferences(
                UserPreferences(
                    windowWidth = windowState.size.width,
                    windowHeight = windowState.size.height,
                    placement = windowState.placement,
                    isMinimized = windowState.isMinimized,
                    positionX = windowState.position.x,
                    positionY = windowState.position.y,
                )
            )
        }
    }
}
