package cs346.views.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import cs346.views.components.AddCourseButton
import cs346.views.components.CourseCard
import cs346.views.theme.PADDING_MEDIUM
import cs346.views.theme.PADDING_SMALL

@Composable
fun CourseListScreen(windowState: WindowState) {
    var courses by remember { mutableStateOf(listOf<CourseCardData>()) }
    val scrollState = rememberLazyGridState()

    val windowWidth = windowState.size.width
    val windowHeight = windowState.size.height

    val cardWidth = windowWidth / 3 - PADDING_MEDIUM
    val cardHeight = windowHeight / 3 - PADDING_MEDIUM

    LazyVerticalGrid(state = scrollState, columns = GridCells.Fixed(3), modifier = Modifier.padding(PADDING_SMALL)) {
        itemsIndexed(courses) { _, courseCardData ->
            Box(
                modifier = Modifier
                    .padding(PADDING_SMALL)
                    .size(cardWidth, cardHeight)
            ) {
                CourseCard(courseCardData, cardWidth = cardWidth, cardHeight = cardHeight)
            }
        }

        item {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(cardWidth, cardHeight)
            ) {
                AddCourseButton {
                    courses = courses + CourseCardData()
                }
            }
        }

    }
}

data class CourseCardData(
    val editable: MutableState<Boolean> = mutableStateOf(true),
    val courseCode: MutableState<String> = mutableStateOf(""),
    val schedule: MutableState<String> = mutableStateOf("")
)
