package cs346.views.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cs346.controller.NavController
import cs346.model.Screen
import cs346.views.pages.CourseCardData
import cs346.views.theme.ExtendedTheme

@Composable
fun CourseCard(navController: NavController, courseCardData: CourseCardData, cardWidth: Dp = 300.dp, cardHeight: Dp = 222.dp) {
    // TODO: Randomly generate color on creation
    CourseCardContainer(
        thumbnailColor = ExtendedTheme.colors.primary,
        cardWidth,
        cardHeight,
    ) {
        if (courseCardData.editable.value) {
            CourseCardEditableText(navController, courseCardData)
        } else {
            CourseCardStaticText(courseCardData)
        }
    }
}

@Composable
private fun CourseCardEditableText(navController: NavController, courseCardData: CourseCardData) {
    val focusRequester: FocusRequester = FocusRequester()
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val onEnterAction = {
        if (courseCardData.courseCode.value.isNotEmpty()) {
            courseCardData.editable.value = false
            navController.navigate(Screen.CourseScreen.route.replace("{courseId}", "1"))
        }
    }
    val keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    val keyboardActions = KeyboardActions(onDone = { onEnterAction() })

    BasicTextField(
        value = courseCardData.courseCode.value,
        onValueChange = { if (it.length < COURSE_CODE_MAX_LEN) courseCardData.courseCode.value = it },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = Modifier
            .padding(start = 16.dp, bottom = 8.dp, top = 12.73.dp)
            .testTag(COURSE_CODE_TEST_TAG)
            .focusRequester(focusRequester),
        textStyle = ExtendedTheme.typography.cardHeading(false),
    )
    Text(
        text = courseCardData.schedule.value,
        maxLines = 1,
        modifier = Modifier
            .padding(start = 16.dp)
            .testTag(COURSE_SCHEDULE_TEST_TAG),
        style = ExtendedTheme.typography.cardSubheading(false),
    )
}

@Composable
private fun CourseCardStaticText(courseCardData: CourseCardData) {
    Text(
        text = courseCardData.courseCode.value,
        modifier = Modifier
            .padding(start = 16.dp, bottom = 8.dp, top = 12.73.dp),
        style = ExtendedTheme.typography.cardHeading(false),
    )
    Text(
        text = courseCardData.schedule.value,
        modifier = Modifier
            .padding(start = 16.dp),
        style = ExtendedTheme.typography.cardSubheading(false),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CourseCardContainer(
    thumbnailColor: Color,
    cardWidth: Dp,
    cardHeight: Dp,
    textComponent: @Composable () -> Unit
) {

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = ExtendedTheme.colors.background,
        ),
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .size(cardWidth, cardHeight),
    ) {
        Box(
            modifier = Modifier
                .testTag("courseCardContainer")
                .size(cardWidth, cardHeight / 3 * 2)
                .background(
                    color = thumbnailColor,
                    shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
                )
        ) {}
        textComponent()
    }
}
