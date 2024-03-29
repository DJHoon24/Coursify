package cs346.views.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import cs346.controller.NavController
import cs346.model.Screen
import cs346.model.User
import cs346.model.UserPreferences
import cs346.model.UserPreferences.convertLectureInfo
import cs346.views.components.AddCourseButton
import cs346.views.components.CourseCard
import cs346.views.theme.LocalExtendedColors
import cs346.views.theme.PADDING_MEDIUM
import cs346.views.theme.PADDING_SMALL

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CourseListScreen(navController: NavController) {
    var courses by remember { mutableStateOf(User.courses) }

    val scrollState = rememberLazyGridState()

    val windowInfo = LocalWindowInfo.current
    val windowSize = windowInfo.containerSize
    val windowWidth = windowSize.width.dp
    val windowHeight = windowSize.height.dp

    val cardWidth = windowWidth / 3 - PADDING_MEDIUM
    val cardHeight = windowHeight / 5 - PADDING_MEDIUM

    Column(modifier = Modifier.fillMaxSize().background(LocalExtendedColors.current.colorScheme.pageBackground)) {
        LazyVerticalGrid(
            state = scrollState,
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(PADDING_SMALL)
        ) {
            itemsIndexed(courses) { _, courseCardData ->
                Box(
                    modifier = Modifier
                        .padding(PADDING_SMALL)
                        .size(cardWidth, cardHeight)
                        .clickable {
                            navController.navigate(
                                Screen.CourseScreen.route.replace(
                                    "{courseId}",
                                    courseCardData.id.toString()
                                )
                            )
                        }
                ) {
                    CourseCard(
                        navController,
                        CourseCardData(
                            id = courseCardData.id,
                            editable = mutableStateOf(
                                courseCardData.courseNumber.isEmpty()
                            ),
                            courseCode = mutableStateOf(courseCardData.courseNumber),
                            schedule = mutableStateOf(convertLectureInfo(courseCardData.lectureInfo, UserPreferences.timeFormat24H.value))
                        ),
                        cardWidth = cardWidth,
                        cardHeight = cardHeight
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(cardWidth, cardHeight)
                ) {
                    AddCourseButton {
                        navController.navigate(Screen.CourseScreen.route)
                    }
                }
            }
        }
    }
}

data class CourseCardData(
    val id: Int,
    val editable: MutableState<Boolean> = mutableStateOf(true),
    val courseCode: MutableState<String> = mutableStateOf(""),
    val schedule: MutableState<String> = mutableStateOf("")
)
