package cs346.views.pages

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
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
import cs346.model.*
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
fun CourseScreen(navController: NavController, id: Int? = null) {
    println("CourseScreen")
    var courseId = if (id === null) {
        -1
    } else {
        id
    };
    var storedCourseCode = User.courses.getById(courseId)?.courseNumber ?: ""
    var storedLectureSchedule = User.courses.getById(courseId)?.lectureInfo ?: ""
    var storedInstructorInfo = User.courses.getById(courseId)?.instructors ?: ""
    var storedCourseDescription = User.courses.getById(courseId)?.courseDescription ?: ""
    var storedReview = User.courses.getById(courseId)?.review ?: ""
    var storedRating = User.courses.getById(courseId)?.rating ?: 0

    var courseCode = remember { mutableStateOf(storedCourseCode) }
    var lectureSchedule = remember { mutableStateOf("Lectures: " + storedLectureSchedule) }
    var instructorInfo = remember { mutableStateOf("Professors: " + storedInstructorInfo) }
    var courseDescription = remember { mutableStateOf(storedCourseDescription) }
    var review = remember { mutableStateOf(storedReview) }
    var rating = remember { mutableStateOf(storedRating) }

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
        Row(modifier = Modifier.fillMaxWidth().background(ExtendedTheme.colors.primary)) {
            Button(onClick = {
                navController.navigate(Screen.CourseListScreen.route)
            }, modifier = Modifier.padding(PADDING_MEDIUM)) {
                Text("List Page")
            }

            Button(
                onClick = {
                    if (courseId == -1) {
                        val newCourseId = User.courses.findNextID()
                        User.courses.add(
                            courseNumber = courseCode.value,
                            lectureInfo = lectureSchedule.value,
                            instructors = instructorInfo.value,
                            courseDescription = courseDescription.value,
                            review = review.value,
                            rating = rating.value,
                        )
                        courseId = newCourseId
                    } else {
                        User.courses.editCourseNumber(courseCode.value, courseId)
                        User.courses.editLectureInfo(lectureSchedule.value.replace("Lectures: ", ""), courseId)
                        User.courses.editInstructors(instructorInfo.value.replace("Professors: ", ""), courseId)
                        User.courses.editCourseDescription(courseDescription.value, courseId)
                        User.courses.editReview(review.value, courseId)
                        User.courses.editRating(rating.value, courseId)
                    }
                    navController.navigate(Screen.CourseListScreen.route)
                },
                modifier = Modifier.padding(PADDING_MEDIUM),

                ) {
                Text("Save")
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.2f)
                .background(brush = ExtendedTheme.colors.fadedBackground)
        ) {
            BasicTextField(
                value = courseCode.value,
                onValueChange = {
                    courseCode.value = it
                    User.courses.editCourseNumber(it, courseId)
                },
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
                value = lectureSchedule.value,
                onValueChange = {
                    if (!it.startsWith("Lectures: ")) {
                        lectureSchedule.value = "Lectures: "
                    } else {
                        lectureSchedule.value = it
                        val parsedString = it.substring(it.indexOf("Lectures: "))
                        User.courses.editLectureInfo(parsedString, courseId)
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
                value = instructorInfo.value,
                onValueChange = {
                    if (!it.startsWith("Professors: ")) {
                        instructorInfo.value = "Professors: "
                    } else {
                        instructorInfo.value = it
                        val parsedString = it.substring(it.indexOf("Professors: "))
                        User.courses.editLectureInfo(parsedString, courseId)
                    }
                },
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                textStyle = ExtendedTheme.typography.courseSubheading,
                modifier = Modifier
                    .padding(PADDING_LARGE, PADDING_MEDIUM, 0.dp, 0.dp),
                singleLine = true,
            )
            ExpandableTextField("Description", courseDescription)
            ExpandableTextField("Review", review)
            StarRating(rating)
        }

        // Table Containers
        Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
            NotesTable(User.courses.getById(courseId)?.notes?.toTypedArray(), navController, courseId)
        }

        Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
            AssignmentsTable(User.courses.getById(courseId)?.assignments?.toTypedArray(), courseId)
        }
    }
}