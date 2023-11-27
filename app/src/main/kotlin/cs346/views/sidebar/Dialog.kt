package cs346.views.sidebar

//import cs346.model.editLectureInfoTimeFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cs346.model.UserPreferences
import cs346.model.UserTheme
import cs346.views.theme.ExtendedTheme
import cs346.views.theme.LocalExtendedColors

enum class Tabs {
    Preferences,
    Help
}

@Composable
fun DialogContainer(isOpen: MutableState<Boolean>) {
    var currentTab = mutableStateOf(Tabs.Preferences)

    if (isOpen.value) {
        Dialog(
            onDismissRequest = { isOpen.value = false },
            DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(0.7f)
                    .padding(16.dp)
                    .background(Color.White, RoundedCornerShape(6.dp))
            ) {
                Row(modifier = Modifier.fillMaxSize()) {

                    // Side menu bar
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.2f),
                        backgroundColor = LocalExtendedColors.current.colorScheme.pageBackground,
                        shape = RoundedCornerShape(
                            topStart = 6.dp,
                            topEnd = 0.dp,
                            bottomStart = 6.dp,
                            bottomEnd = 0.dp
                        )

                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            TextButton(
                                onClick = { currentTab.value = Tabs.Preferences },
                                modifier = Modifier
                                    .padding(top = 64.dp)
                                    .fillMaxWidth()
                                    .background(
                                        color = if (currentTab.value === Tabs.Preferences) {
                                            LocalExtendedColors.current.colorScheme.componentAccent
                                        } else {
                                            LocalExtendedColors.current.colorScheme.pageBackground
                                        }
                                    )
                            ) {
                                Text("Preferences", style = ExtendedTheme.typography.cardHeading(true), maxLines = 1)
                            }
                            TextButton(
                                onClick = { currentTab.value = Tabs.Help },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = if (currentTab.value === Tabs.Help) {
                                            LocalExtendedColors.current.colorScheme.componentAccent
                                        } else {
                                            LocalExtendedColors.current.colorScheme.pageBackground
                                        },
                                    )
                            ) {
                                Text("Help", style = ExtendedTheme.typography.cardHeading(true))
                            }
                        }
                    }

                    // Tab content
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = LocalExtendedColors.current.colorScheme.background)
                    ) {
                        when (currentTab.value) {
                            Tabs.Preferences -> PreferencesTabContent()
                            Tabs.Help -> HelpTabContent()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PreferencesTabContent() {
    Text(
        "Theme",
        style = ExtendedTheme.typography.sidebarCourse,
        modifier = Modifier.padding(top = 64.dp, start = 24.dp),
    )
    DropdownSelect(
        List(UserTheme.values().size) { UserTheme.values()[it].name },
        UserPreferences.userTheme.name,
        onChange = { option ->
            UserPreferences.userTheme = UserTheme.valueOf(option)
            UserPreferences.savePreferences()
        }
    )
    Text(
        "Time Format",
        style = ExtendedTheme.typography.sidebarCourse,
        modifier = Modifier.padding(top = 64.dp, start = 24.dp),
    )
    DropdownSelect(
        listOf("12 Hour", "24 Hour"),
        if (UserPreferences.timeFormat24H.value) "24 Hour" else "12 Hour",
        onChange = { option ->
            UserPreferences.timeFormat24H.value = option === "24 Hour"
            UserPreferences.savePreferences()
        }
    )
}

@Composable
private fun HelpTabContent() {
    Text(
        "Adding a Course",
        style = ExtendedTheme.typography.dialogSubheading,
        modifier = Modifier.padding(top = 64.dp, start = 24.dp, bottom = 12.dp, end = 24.dp),
    )
    Text(
        "Add a new course by clicking the plus button on the courses list page. This will take you to a new course page where you can fill in all information. Note that after entering the course title Coursify will attempt to load the course description and schedule automatically from the UW Open API.",
        style = ExtendedTheme.typography.dialogBody,
        modifier = Modifier.padding(start = 24.dp, bottom = 12.dp, end = 24.dp),
    )


    Text(
        "Adding/Editing Course Notes",
        style = ExtendedTheme.typography.dialogSubheading,
        modifier = Modifier.padding(start = 24.dp, bottom = 12.dp, end = 24.dp),
    )
    Text(
        "Add a new note for a course by clicking the plus button in the notes table on the course's page. Notes are written in Markdown, with the option to switch between editor and view modes.",
        style = ExtendedTheme.typography.dialogBody,
        modifier = Modifier.padding(start = 24.dp, bottom = 12.dp, end = 24.dp)
    )

    Text(
        "Adding Assignments",
        style = ExtendedTheme.typography.dialogSubheading,
        modifier = Modifier.padding(start = 24.dp, bottom = 12.dp, end = 24.dp),
    )
    Text(
        "Add a new assignment for a course by clicking the plus button in the assignments table on the course's page. Your total weight in the course will be calculated automatically as you add assignment grades.",
        style = ExtendedTheme.typography.dialogBody,
        modifier = Modifier.padding(start = 24.dp, bottom = 12.dp, end = 24.dp)
    )

}

@Composable
private fun DropdownSelect(options: List<String>, initiallySelectedOption: String, onChange: (String) -> Unit = {}) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(initiallySelectedOption) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(0.3f)
    ) {
        // Dropdown field
        Box(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .background(LocalExtendedColors.current.colorScheme.componentAccent, RoundedCornerShape(6.dp))
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                BasicTextField(
                    value = selectedOption,
                    enabled = false,
                    onValueChange = { },
                    textStyle = ExtendedTheme.typography.dropdownText,
                    modifier = Modifier
                        .weight(1f)
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(4.dp)
                )
            }
        }

        // Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .background(Color.White, RoundedCornerShape(6.dp))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption = option
                        expanded = false
                        onChange(option)
                    },
                ) {
                    Text(option, style = ExtendedTheme.typography.dropdownText)
                }
            }
        }
    }
}