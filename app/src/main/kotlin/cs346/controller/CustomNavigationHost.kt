package cs346.controller

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.rememberWindowState
import cs346.model.Screen
import cs346.views.pages.CourseListScreen
import cs346.views.pages.LandingScreen
import cs346.views.pages.MarkdownViewer

@Composable
fun CustomNavigationHost(
    navController: NavController,
//    courses: MutableList<Int>,
//    notes: MutableList<Int>
) {
    val windowState = rememberWindowState()
    NavigationHost(navController) {
        composable(Screen.LandingScreen.route) {
            LandingScreen(navController)
        }

        composable(Screen.CourseListScreen.route) {
            CourseListScreen(windowState)
        }

        composable(Screen.DefaultMarkdownScreen.route) {
            MarkdownViewer(navController)
        }

//        courseIds.forEach { courseId ->
//            val route = Screen.CourseScreen.route.replace("{courseId}", courseId.toString())
//            composable(route) {
//                CourseScreen(courseId, navController)
//            }
//        }
//
//        notes.forEach { note ->
//            val route = Screen.MarkdownScreen.route.replace("{noteId}", note.id.toString())
//            composable(route) {
//                NoteScreen(note, navController)
//            }
//        }

    }.build()
}