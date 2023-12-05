package cs346.views.sidebar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cs346.controller.AuthController
import cs346.controller.DefaultButton
import cs346.controller.NavController
import cs346.model.Course
import cs346.model.Screen
import cs346.model.User
import cs346.views.theme.ExtendedTheme
import cs346.views.theme.LocalExtendedColors
import cs346.views.theme.PADDING_SMALL

@Composable
fun Sidebar(
    navController: NavController,
    onNavigate: () -> Unit
) {
    val userName: String = "${User.firstName} ${User.lastName}" //change to actual username
    val courses: MutableList<Course> = User.courses //make it fetch actual courses
    var isPrefsDialogOpen = remember { mutableStateOf(false) }

    DialogContainer(isPrefsDialogOpen)

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(240.dp)
            .background(LocalExtendedColors.current.colorScheme.background)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = userName,
                style = ExtendedTheme.typography.cardHeading(false),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            IconButton(
                onClick = { isPrefsDialogOpen.value = true },
            ) {
                Icon(
                    Icons.Outlined.Settings,
                    contentDescription = "Settings",
                    tint = Color(0XFF242424),
                )
            }
        }
        Text(
            text = "Courses",
            style = ExtendedTheme.typography.sidebarHeadingText,
            modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxHeight(0.85f)) {
            items(courses.size) { index ->
                CourseItem(courses[index], navController)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        DefaultButton(
            text = "Sign out",
            onClick = {
                AuthController.callRequest {
                    AuthController.updateCourses(
                        User.email,
                        User.courses.toMutableList()
                    )
                }
                User.id = ""
                User.firstName = ""
                User.lastName = ""
                User.email = ""
                User.courses = mutableStateListOf<Course>()
                onNavigate()
            },
            Modifier.padding(PADDING_SMALL),
            contentColor = Color.Black
        )
    }
}

@Composable
fun CourseItem(
    course: Course,
    navController: NavController
) {
    var isExpanded by remember { mutableStateOf(true) }
    val courseRoute = Screen.CourseScreen.route.replace(
        "{courseId}",
        course.id.toString()
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
    ) {
        Text(text = course.courseNumber,
            style = ExtendedTheme.typography.sidebarCourse,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    AuthController.callRequest {
                        AuthController.updateCourses(
                            User.email,
                            User.courses.toMutableList()
                        )
                    }
                    navController.navigate(
                        courseRoute
                    )
                }
                .padding(top = 10.dp, start = 12.dp))
        if (isExpanded) {
            course.notes.forEach { note ->
                val noteRoute = Screen.MarkdownScreen.route.replace("{courseId}", course.id.toString())
                    .replace(
                        "{noteId}",
                        note.id.toString()
                    )
                Text(
                    text = note.title,
                    style = ExtendedTheme.typography.sidebarNote,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            AuthController.callRequest {
                                AuthController.updateCourses(
                                    User.email,
                                    User.courses.toMutableList()
                                )
                            }
                            navController.navigate(noteRoute)
                        }
                        .padding(start = 28.dp, top = 6.dp)
                )
            }
        }
    }
}