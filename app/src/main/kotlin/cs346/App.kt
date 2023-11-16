package cs346

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import cs346.controller.*
import cs346.model.Screen
import cs346.model.UserPreferences
import cs346.views.pages.LandingScreen
import cs346.views.pages.LoginScreen
import cs346.views.sidebar.Sidebar
import java.io.File

enum class CurrentView {
    LandingPage,
    LoginPage,
    LoggedIn
}

@Composable
fun App(preferencesController: UserPreferencesController) {
    val navController by rememberNavController(Screen.CourseListScreen.route)

    var currentView by remember { mutableStateOf(CurrentView.LandingPage) }
    when (currentView) {
        CurrentView.LandingPage -> LandingScreen { currentView = CurrentView.LoginPage }
        CurrentView.LoginPage -> LoginScreen { currentView = CurrentView.LoggedIn }
        CurrentView.LoggedIn -> {
            Row {
                Sidebar(navController)
                Box(modifier = Modifier.fillMaxSize()) {
                    CustomNavigationHost(navController)
                }
            }
        }
    }
}

fun main() = application {
    // TODO: save the user preferences in db instead of local file
    val userHome = System.getProperty("user.home")
    val documentsDirectory = "$userHome/Documents/Coursify"
    val directory = File(documentsDirectory)
    if (!directory.exists()) {
        directory.mkdirs()
    }
    val preferencesController = FileUserPreferencesController(File(documentsDirectory, "user-preferences.txt"))
    val windowState = rememberWindowState()

    ManageUserPreferences(preferencesController, windowState)
    UWOpenAPIController.callRequest {
        UWOpenAPIController.populateTermCourseData()
    }
    Window(
        title = "Coursify",
        state = windowState,
        onCloseRequest = ::exitApplication
    ) {
        App(preferencesController)
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
