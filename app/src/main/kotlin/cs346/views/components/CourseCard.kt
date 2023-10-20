package cs346.views.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import cs346.views.theme.ExtendedTheme

@Composable
fun CourseCard(editable: Boolean = false, courseCode: String = "", schedule: String = "") {
    // TODO: Randomly generate color on creation
    CourseCardContainer(
        thumbnailColor = ExtendedTheme.colors.primary
    ) {
        if (editable) {
            CourseCardEditableText()
        } else {
            CourseCardStaticText(courseCode, schedule)
        }
    }
}

@Composable
private fun CourseCardEditableText() {
    var courseCode by remember { mutableStateOf("") }
    var schedule by remember { mutableStateOf("") }


    BasicTextField(
        value = courseCode,
        onValueChange = { if (it.length < COURSE_CODE_MAX_LEN) courseCode = it},
        modifier = Modifier
            .padding(start = 16.dp, bottom = 8.dp, top = 12.73.dp)
            .testTag(COURSE_CODE_TEST_TAG),
        textStyle = ExtendedTheme.typography.cardHeading,
    )
    BasicTextField(
        value = schedule,
        onValueChange = { if (it.length < COURSE_SCHEDULE_MAX_LEN) schedule = it},
        modifier = Modifier
            .padding(start = 16.dp)
            .testTag(COURSE_SCHEDULE_TEST_TAG),
        textStyle = ExtendedTheme.typography.cardSubheading,
    )
}

@Composable
private fun CourseCardStaticText(courseCode: String, schedule: String) {
    Text(
        text = courseCode,
        modifier = Modifier
            .padding(start = 16.dp, bottom = 8.dp, top = 12.73.dp),
        style = ExtendedTheme.typography.cardHeading,
    )
    Text(
        text = schedule,
        modifier = Modifier
            .padding(start = 16.dp),
        style = ExtendedTheme.typography.cardSubheading,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CourseCardContainer(thumbnailColor: Color, textComponent: @Composable () -> Unit) {
    val cardWidth = 300.dp

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = ExtendedTheme.colors.background,
        ),
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .size(width = cardWidth, height = 222.dp)
    ) {
        Box(
          modifier = Modifier
              .size(cardWidth, 151.27.dp)
              .background(
                  color = thumbnailColor,
                  shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
        ){}
        textComponent()
    }
}
