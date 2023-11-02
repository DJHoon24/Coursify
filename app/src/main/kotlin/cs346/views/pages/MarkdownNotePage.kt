package cs346.views.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
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
import cs346.controller.NavController
import cs346.model.Note
import cs346.model.Screen
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
            BasicTextField(
                value = title.value,
                onValueChange = { title.value = it },
                modifier = Modifier
                    .testTag(NOTE_TITLE_TEST_TAG)
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(color = Color.Transparent)
                    .padding(PADDING_SMALL),
                textStyle = ExtendedTheme.typography.noteTitle,
                singleLine = true,
            )
        }
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Button(
                onClick = {
                    editorMode = if (editorMode == EditorMode.Editing) EditorMode.Viewing else EditorMode.Editing
                },
                modifier = Modifier.padding(PADDING_SMALL)
            ) {
                Text(text = if (editorMode == EditorMode.Editing) "Edit Mode" else "View Mode")
            }

            Button(
                onClick = {
                    if (note == null) {
                        println("Save New Note")
                        // Save new note in courseID
                    } else {
                        println("Edit pre-existing note")
                        // Find NoteID and corresponding CourseID in model class and call edit note or create new note.
                    }
                    navController.navigate(Screen.CourseListScreen.route)
                },
                modifier = Modifier.padding(PADDING_SMALL)
            ) {
                Text(text = "Save")
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
        // Wrap the selected text with the markdown symbol
        val newText = textFieldValue.text.substring(0, selection.end) +
                markdownSymbol + textFieldValue.text.substring(selection.end, selection.start) +
                markdownSymbol + textFieldValue.text.substring(selection.start)
        return textFieldValue.copy(
            text = newText,
            selection = TextRange(selection.start + markdownSymbol.length, selection.end + markdownSymbol.length)
        )
    }
}