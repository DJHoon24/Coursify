package cs346.views.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.RichText
@Composable
fun MarkdownViewer() {
    val markdownText = remember { mutableStateOf("") }

    Row {
        TextField(
            value = markdownText.value,
            onValueChange = { markdownText.value = it }
        )
        RichText(
        ) {
            Markdown(markdownText.value)
        }
    }
}
