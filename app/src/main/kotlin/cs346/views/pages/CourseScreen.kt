package cs346.views.pages

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cs346.controller.NavController
import cs346.model.Course
import cs346.views.components.tables.AssignmentsTable
import cs346.views.components.tables.NotesTable
import cs346.views.theme.ExtendedTheme
import cs346.views.theme.PADDING_LARGE
import cs346.views.theme.PADDING_MEDIUM
import cs346.views.theme.PADDING_SMALL

@Composable
fun ExpandableTextField(title: String, content: MutableState<String>) {
    var isExpanded by remember { mutableStateOf(false) }
    var rotationState by remember { mutableStateOf(-90f) }

    val rotation by animateFloatAsState(
            targetValue = if (isExpanded) 180f else 0f
    )

    Column(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically

        ) {
            IconButton(
                    onClick = {
                        isExpanded = !isExpanded
                        rotationState += 90f
                    },
                    modifier = Modifier.rotate(rotation)
            ) {
                Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand",
                        modifier = Modifier
                                .size(24.dp),
                        tint = Color.White

                )
            }
            Text(text = title, style = ExtendedTheme.typography.courseSubheading)
        }

        if (isExpanded) {
            BasicTextField(
                    value = content.value,
                    onValueChange = { content.value = it },
                    textStyle = ExtendedTheme.typography.courseBody,
                    modifier = Modifier.padding(48.dp, PADDING_SMALL, 0.dp, 0.dp)
            )
        }
    }
}

@Composable
fun StarRating(rating: MutableState<Int>) {

    Row(
            modifier = Modifier.padding(PADDING_LARGE, 0.dp, 0.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Rating: ", style = ExtendedTheme.typography.courseSubheading)
        for (i in 1..5) {
            IconButton(
                    onClick = { rating.value = i },
                    modifier = Modifier.size(36.dp)
            ) {
                if (i <= rating.value) {
                    Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Selected",
                            modifier = Modifier.size(24.dp),
                            tint = Color.White

                    )
                } else {
                    Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Unselected",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Gray
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CourseScreen(course: Course, navController: NavController) {
    var courseCode by remember { mutableStateOf(course.courseNumber) }
    var lectureSchedule by remember { mutableStateOf("Lectures: " + course.lectureInfo) }
    var instructorInfo by remember { mutableStateOf("Professors: " + course.instructors) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val onEnterAction = {
        focusManager.clearFocus()
    }
    val keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    val keyboardActions = KeyboardActions(onDone = { onEnterAction() })

    Column(
            modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(color = ExtendedTheme.colors.pageBackground)
                    .onClick { focusManager.clearFocus() }
                    .verticalScroll(rememberScrollState())
    ) {
        // Course Code Container
        Box(
                modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.2f)
                        .background(brush = ExtendedTheme.colors.fadedBackground)
        ) {
            BasicTextField(
                    value = courseCode,
                    onValueChange = { courseCode = it },
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    textStyle = ExtendedTheme.typography.pageTitle,
                    modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(PADDING_LARGE)
                            .focusRequester(focusRequester),
                    singleLine = true,
            )
        }

        // Other Course Info Container
        Column(
                modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.5f),
        ) {
            BasicTextField(
                    value = lectureSchedule,
                    onValueChange = {
                        if (!it.startsWith("Lectures: ")) {
                            lectureSchedule = "Lectures: "
                        } else {
                            lectureSchedule = it
                        }
                    },
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    textStyle = ExtendedTheme.typography.courseSubheading,
                    modifier = Modifier
                            .padding(PADDING_LARGE, PADDING_MEDIUM, 0.dp, 0.dp),
                    singleLine = true,
            )

            BasicTextField(
                    value = instructorInfo,
                    onValueChange = {
                        if (!it.startsWith("Professors: ")) {
                            instructorInfo = "Professors: "
                        } else {
                            instructorInfo = it
                        }
                    },
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    textStyle = ExtendedTheme.typography.courseSubheading,
                    modifier = Modifier
                            .padding(PADDING_LARGE, PADDING_MEDIUM, 0.dp, 0.dp),
                    singleLine = true,
            )
            ExpandableTextField("Description", mutableStateOf(course.courseDescription))
            ExpandableTextField("Review", mutableStateOf(course.review))
            StarRating(mutableStateOf(course.rating))
        }

        // Table Containers
        Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
            NotesTable(course.notes.toTypedArray())
        }

        Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
            AssignmentsTable(course.assignments.toTypedArray())
        }
    }
}