package cs346.views.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.RichText
import cs346.controller.AuthController
import cs346.controller.DefaultButton
import cs346.controller.NavController
import cs346.model.*
import cs346.views.components.MARKDOWN_EDIT_TEST_TAG
import cs346.views.components.MARKDOWN_VIEW_TEST_TAG
import cs346.views.components.NOTE_TITLE_TEST_TAG
import cs346.views.theme.ExtendedTheme
import cs346.views.theme.PADDING_MEDIUM
import cs346.views.theme.PADDING_SMALL

enum class EditorMode {
    Editing, Viewing
}

@Composable
fun MarkdownViewer(navController: NavController, note: Note? = null, courseID: Int = 0) {
    val markdownText = remember { mutableStateOf(TextFieldValue(note?.content ?: "")) }
    val title = remember { mutableStateOf(note?.title ?: "") }
    var editorMode by remember { mutableStateOf(EditorMode.Editing) }

    Column(modifier = Modifier.padding(PADDING_MEDIUM)) {
        Box(
            modifier = Modifier
                .fillMaxHeight(fraction = 0.2f)
                .fillMaxWidth()
                .background(brush = ExtendedTheme.colors.fadedBackground)
        ) {
            Column {
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    DefaultButton(
                        text = if (editorMode == EditorMode.Editing) "View Mode" else "Edit Mode",
                        onClick = {
                            editorMode =
                                if (editorMode == EditorMode.Editing) EditorMode.Viewing else EditorMode.Editing
                        },
                        modifier = Modifier.padding(PADDING_SMALL)
                    )

                    DefaultButton(
                        text = "Save",
                        onClick = {
                            if (note == null) {
                                User.courses.getById(courseID)?.notes?.addNote(
                                    title.value,
                                    markdownText.value.text
                                )
                            } else {
                                User.courses.getById(courseID)?.notes?.edit(
                                    title.value,
                                    markdownText.value.text,
                                    note.id
                                )
                            }
                            AuthController.callRequest {
                                AuthController.updateCourses(
                                    User.email,
                                    User.courses.toMutableList()
                                )
                            }
                            navController.navigate(Screen.CourseListScreen.route)
                        },
                        modifier = Modifier.padding(PADDING_SMALL)
                    )
                    if (note != null) {
                        DefaultButton(
                            text = "Delete",
                            onClick = {
                                User.courses.getById(courseID)?.notes?.remove(note)
                                AuthController.callRequest {
                                    AuthController.updateCourses(
                                        User.email,
                                        User.courses.toMutableList()
                                    )
                                }
                                navController.navigate(Screen.CourseListScreen.route)
                            },
                            modifier = Modifier.padding(PADDING_SMALL)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brush = ExtendedTheme.colors.fadedBackground)
                ) {
                    BasicTextField(
                        value = title.value,
                        onValueChange = {
                            title.value = it
                        },
                        modifier = Modifier
                            .testTag(NOTE_TITLE_TEST_TAG)
                            .fillMaxWidth()
                            .align(Alignment.BottomStart)
                            .background(color = Color.Transparent)
                            .padding(PADDING_SMALL),
                        textStyle = ExtendedTheme.typography.noteTitle,
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (title.value.isEmpty()) {
                                Text(
                                    text = "Note Title:",
                                    color = Color.Gray,
                                    style = ExtendedTheme.typography.noteTitle
                                )
                            }
                            innerTextField()  // This will draw the actual BasicTextField
                        }
                    )
                }
            }
        }

        Row {
            if (editorMode == EditorMode.Editing) {
                TextField(
                    value = markdownText.value,
                    onValueChange = { markdownText.value = it },
                    modifier = Modifier
                        .testTag(MARKDOWN_EDIT_TEST_TAG)
                        .fillMaxHeight()
                        .border(2.dp, ExtendedTheme.colors.background)
                        .fillMaxWidth(fraction = 0.5f)
                        .onPreviewKeyEvent { keyEvent ->
                            if (keyEvent.type == KeyEventType.KeyDown) {
                                val isCtrlPressed = keyEvent.isCtrlPressed or keyEvent.isMetaPressed
                                when (keyEvent.key) {
                                    Key.B -> if (isCtrlPressed) {
                                        markdownText.value = applyMarkdownFormatting(markdownText.value, "**")
                                        true // Indicate that the event is consumed
                                    } else {
                                        false
                                    }

                                    Key.I -> if (isCtrlPressed) {
                                        // Apply italic formatting
                                        markdownText.value = applyMarkdownFormatting(markdownText.value, "*")
                                        true
                                    } else {
                                        false
                                    }

                                    Key.Five -> if (isCtrlPressed) {
                                        // Apply italic formatting
                                        markdownText.value = applyMarkdownFormatting(markdownText.value, "~")
                                        true
                                    } else {
                                        false
                                    }

                                    else -> {
                                        false
                                    }
                                }
                            } else false
                        },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,  // This is the custom background color
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    textStyle = ExtendedTheme.typography.noteBody,
                )
            }
            Box(
                modifier = Modifier
                    .testTag(MARKDOWN_VIEW_TEST_TAG)
                    .fillMaxSize() // Take up the leftover space
                    .background(Color.White)
                    .border(2.dp, ExtendedTheme.colors.background)
                    .padding(PADDING_MEDIUM)// Set background color
            ) {
                RichText(
                    modifier = Modifier
                        .verticalScroll(state = rememberScrollState())
                        .fillMaxWidth()
                ) {
                    Markdown(markdownText.value.text)
                }
            }
        }
    }
}

// Utility function to apply Markdown formatting
fun applyMarkdownFormatting(textFieldValue: TextFieldValue, markdownSymbol: String): TextFieldValue {
    val selection = textFieldValue.selection
    if (selection.start == selection.end) {
        // No text is selected, simply insert the symbol
        return textFieldValue.copy(
            text = textFieldValue.text.substring(0, selection.start) +
                    markdownSymbol + markdownSymbol + textFieldValue.text.substring(selection.start),
            selection = TextRange(selection.start + markdownSymbol.length)
        )
    } else {
        var leftIdx = selection.start
        var rightIdx = selection.end
        if (selection.end < selection.start) {
            leftIdx = selection.end
            rightIdx = selection.start
        }
        // Wrap the selected text with the markdown symbol
        val newText = textFieldValue.text.substring(0, leftIdx) +
                markdownSymbol + textFieldValue.text.substring(leftIdx, rightIdx) +
                markdownSymbol + textFieldValue.text.substring(rightIdx)
        return textFieldValue.copy(
            text = newText,
            selection = TextRange(leftIdx + markdownSymbol.length, rightIdx + markdownSymbol.length)
        )
    }
}