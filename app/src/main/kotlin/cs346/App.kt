package cs346

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import cs346.controller.FileUserPreferencesController
import cs346.controller.UserPreferencesController
import cs346.model.Note
import cs346.model.UserPreferences
import cs346.views.pages.MarkdownViewer
import cs346.views.pages.CourseListPage
import cs346.views.pages.LandingPage
import cs346.views.theme.PADDING_SMALL
import java.io.File

enum class CurrentView {
    CourseList,
    MarkdownViewer,
    LandingPage
}
fun main() = application {
    // TODO: save the user preferences in db instead of local file
    val userHome = System.getProperty("user.home")
    val desktopDirectory = "$userHome/Desktop"
    val preferencesController = FileUserPreferencesController(File(desktopDirectory, "user-preferences.txt"))
    val windowState = rememberWindowState()
    var currentView by remember { mutableStateOf(CurrentView.LandingPage) }
    var currentNote = Note(1, "LOL", " Hello")
    ManageUserPreferences(preferencesController, windowState)

    Window(
        title = "Coursify",
        state = windowState,
        onCloseRequest = ::exitApplication
    ) {
        Row {
            when (currentView) {
                CurrentView.LandingPage -> LandingPage(
                    onNavigate = { currentView = CurrentView.CourseList }
                )
                CurrentView.CourseList ->
                    Column {
                        Button(
                            onClick = {currentView = CurrentView.MarkdownViewer},
                            modifier = Modifier.padding(PADDING_SMALL)
                        ) {
                            Text(text="Make a New Note")
                        }
                        CourseListPage(windowState)
                    }
                CurrentView.MarkdownViewer -> MarkdownViewer(
                    onNavigate = { currentView = CurrentView.CourseList }
                )
            }
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