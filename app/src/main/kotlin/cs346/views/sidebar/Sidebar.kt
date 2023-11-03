package cs346.views.sidebar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cs346.controller.NavController
import cs346.model.Course
import cs346.model.Screen
import cs346.model.User
import cs346.views.theme.ExtendedTheme

@Composable
fun Sidebar(
    navController: NavController,
) {
    val userName: String = "Paul Oh" //change to actual username
    val courses: MutableList<Course> = User.courses //make it fetch actual courses
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(240.dp)
            .background(ExtendedTheme.colors.background)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier.size(50.dp).background(Color(0xFFD8D8D8), shape = CircleShape)
            )
            Text(text = userName, style = ExtendedTheme.typography.cardHeading(false))
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings"
            )
        }
        Text(
            text = "Courses",
            style = ExtendedTheme.typography.sidebarHeadingText,
            modifier = Modifier.padding(top = 24.dp, bottom = 4.dp)
        )

        LazyColumn {
            items(courses.size) { index ->
                CourseItem(courses[index], navController)
            }
        }
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
                        .clickable { navController.navigate(noteRoute) }
                        .padding(start = 28.dp, top = 6.dp)
                )
            }
        }
    }
}