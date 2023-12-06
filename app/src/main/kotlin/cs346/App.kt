package cs346

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import cs346.controller.CustomNavigationHost
import cs346.controller.UWOpenAPIController
import cs346.controller.rememberNavController
import cs346.model.Screen
import cs346.model.UserPreferences
import cs346.views.pages.LandingScreen
import cs346.views.pages.LoginScreen
import cs346.views.sidebar.Sidebar
import cs346.views.theme.LocalExtendedColors
import cs346.views.theme.getExtendedColors

enum class CurrentView {
    LandingPage,
    LoginPage,
    LoggedIn
}

@Composable
fun App(windowState: WindowState) {
    val navController by rememberNavController(Screen.CourseListScreen.route)

    var currentView by remember { mutableStateOf(CurrentView.LandingPage) }
    when (currentView) {
        CurrentView.LandingPage -> LandingScreen { currentView = CurrentView.LoginPage }
        CurrentView.LoginPage -> LoginScreen { currentView = CurrentView.LoggedIn }
        CurrentView.LoggedIn -> {
            Row {
                Sidebar(navController, { currentView = CurrentView.LandingPage }, windowState)
                Box(modifier = Modifier.fillMaxSize()) {
                    CustomNavigationHost(navController)
                }
            }
        }
    }
}

fun main() = application {
    val observedUserTheme by rememberUpdatedState(newValue = UserPreferences.userTheme)
    val windowState = rememberWindowState()

    ManageUserPreferences(windowState)
    UWOpenAPIController.callRequest {
        UWOpenAPIController.populateTermCourseData()
    }
    Window(
        title = "Coursify",
        state = windowState,
        onCloseRequest = ::exitApplication
    ) {
        CompositionLocalProvider(
            LocalExtendedColors provides getExtendedColors(observedUserTheme)
        ) {
            println("re-render")
            App(windowState)
        }
    }
}


@Composable
fun ManageUserPreferences(windowState: WindowState) {
    UserPreferences.loadPreferences()

    val userPreferences = remember { mutableStateOf(UserPreferences) }
    windowState.apply {
        size = DpSize(userPreferences.value.windowWidth, userPreferences.value.windowHeight)
        placement = userPreferences.value.placement
        isMinimized = userPreferences.value.isMinimized
        position = WindowPosition(userPreferences.value.positionX, userPreferences.value.positionY)
    }

    DisposableEffect(windowState) {
        onDispose {
            UserPreferences.windowWidth = windowState.size.width
            UserPreferences.windowHeight = windowState.size.height
            UserPreferences.placement = windowState.placement
            UserPreferences.isMinimized = windowState.isMinimized
            UserPreferences.positionX = windowState.position.x
            UserPreferences.positionY = windowState.position.y

            UserPreferences.savePreferences(windowState)
        }
    }
}
