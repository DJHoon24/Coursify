package cs346.controller

import androidx.compose.runtime.Composable
import cs346.model.Course
import cs346.model.Screen
import cs346.model.User
import cs346.views.pages.CourseListScreen
import cs346.views.pages.CourseScreen
import cs346.views.pages.MarkdownViewer

@Composable
fun CustomNavigationHost(
    navController: NavController,
) {
    NavigationHost(navController) {
        composable(Screen.CourseListScreen.route) {
            CourseListScreen(navController)
        }

        composable(Screen.CourseScreen.route) {
            CourseScreen(navController)
        }

        User.courses.forEach { course: Course ->
            val courseRoute = Screen.CourseScreen.route.replace("{courseId}", course.id.toString())
            composable(courseRoute) {
                CourseScreen(navController, course.id)
            }

            course.notes.forEach { note ->
                val noteRoute = Screen.MarkdownScreen.route
                    .replace("{courseId}", course.id.toString())
                    .replace("{noteId}", note.id.toString())
                composable(noteRoute) {
                    MarkdownViewer(navController, note, course.id)
                }
            }

            composable(Screen.RootMarkdownScreen.route.replace("{courseId}", course.id.toString())) {
                MarkdownViewer(navController = navController, courseID = course.id)
            }
        }
    }.build()
}