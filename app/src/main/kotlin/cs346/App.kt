package cs346

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import cs346.controller.FileUserPreferencesController
import cs346.controller.UserPreferencesController
import cs346.model.UserPreferences
import cs346.views.components.CourseCard
import cs346.views.components.MarkdownViewer
import java.io.File

fun main() = application {
    // TODO: save the user preferences in db instead of local file
    val userHome = System.getProperty("user.home")
    val desktopDirectory = "$userHome/Desktop"
    val preferencesController = FileUserPreferencesController(File(desktopDirectory,"user-preferences.txt"))
    val windowState = rememberWindowState()

    ManageUserPreferences(preferencesController, windowState)

    Window(
        title = "Coursify",
        state = windowState,
        onCloseRequest = ::exitApplication
    ) {
        Column {
            CourseCard(editable = true)
            MarkdownViewer()
        }
    }
}

//TODO: Implement user preferences for time format 24h and theme
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