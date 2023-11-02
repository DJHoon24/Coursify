package cs346.views.components

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
import androidx.compose.ui.unit.dp
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.RichText
import cs346.views.theme.ExtendedTheme
import cs346.views.theme.PADDING_MEDIUM
import cs346.views.theme.PADDING_SMALL

enum class EditorMode {
    Editing, Viewing
}
@Composable
fun MarkdownViewer(onNavigate: () -> Unit) {
    val markdownText = remember { mutableStateOf("") }
    val title = remember { mutableStateOf("") }
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
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(color = Color.Transparent)
                    .padding(PADDING_SMALL),
                textStyle = ExtendedTheme.typography.noteTitle,
                singleLine = true,
            )
        }
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
            Button(
                onClick = {
                    editorMode = if (editorMode == EditorMode.Editing) EditorMode.Viewing else EditorMode.Editing
                },
                modifier = Modifier.padding(PADDING_SMALL)
            ) {
                Text(text = if (editorMode == EditorMode.Editing) "Edit Mode" else "View Mode")
            }

            Button(
                onClick = onNavigate,
                modifier = Modifier.padding(PADDING_SMALL)
            ) {
                Text(text="Save")
            }
        }

        Row {
            if (editorMode == EditorMode.Editing) {
                TextField(
                    value = markdownText.value,
                    onValueChange = { markdownText.value = it },
                    modifier = Modifier
                        .fillMaxHeight()
                        .border(2.dp, ExtendedTheme.colors.background)
                        .fillMaxWidth(fraction = 0.5f),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,  // This is the custom background color
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    textStyle = ExtendedTheme.typography.noteBody
                )
            }
            Box(
                modifier = Modifier
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
                    Markdown(markdownText.value)
                }
            }
        }
    }
}
