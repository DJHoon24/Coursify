package cs346.views.components.tables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cs346.controller.AuthController
import cs346.controller.NavController
import cs346.model.*
import cs346.views.components.NOTES_TABLE_ROW_TEST_TAG
import cs346.views.components.TABLE_ROW_HEIGHT
import cs346.views.theme.ExtendedTheme
import cs346.views.theme.LocalExtendedColors
import cs346.views.theme.dateFormat
import cs346.views.theme.getLocalDateTime

data class NoteTableCell(
    val id: Int,
    val state: MutableState<String> = mutableStateOf(""),
    val weight: Float,
    val isFilled: MutableState<Boolean> = mutableStateOf(false),
    val keyboardOptions: KeyboardOptions? = null,
    val keyboardActions: KeyboardActions? = null,
)

data class NoteTableRow(
    val nameCell: NoteTableCell,
    val modifiedDateCell: NoteTableCell,
    val createdDateCell: NoteTableCell,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesTable(data: Array<Note>? = null, navController: NavController, courseId: Int) {
    val noteCellWeight = 0.4f
    val modifiedCellWeight = 0.3f
    val createdCellWeight = 0.3f

    val headingCells = arrayOf(
        HeadingCell("Note", TextType.STRING, noteCellWeight),
        HeadingCell("Modified", TextType.STRING, modifiedCellWeight),
        HeadingCell("Created", TextType.STRING, createdCellWeight)
    )

    val transformedRowData = data?.map {
        arrayOf(
            NoteTableCell(id = it.id, mutableStateOf(it.title), noteCellWeight),
            NoteTableCell(id = it.id, mutableStateOf(it.lastModifiedDateTime), modifiedCellWeight),
            NoteTableCell(id = it.id, mutableStateOf(it.createdDateTime), createdCellWeight),
        )
    }
    var tableData by remember { mutableStateOf(transformedRowData) }

    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        // Header row
        stickyHeader {
            Row(Modifier.background(LocalExtendedColors.current.colorScheme.primary).testTag(NOTES_TABLE_ROW_TEST_TAG)) {
                headingCells.map {
                    HeadingCell(text = it.title, weight = it.weight)
                }
            }
        }

        // Core table rows
        tableData?.let {
            items(it.size) {
                Row(Modifier.fillMaxWidth().testTag(NOTES_TABLE_ROW_TEST_TAG)) {
                    NoteTableEditableCell(
                        tableData!![it][0].id,
                        courseId,
                        navController,
                        tableData!![it][0].state,
                        noteCellWeight,
                    )
                    NoteTableStaticCell(tableData!![it][1].state, modifiedCellWeight)
                    NoteTableStaticCell(tableData!![it][2].state, createdCellWeight)
                }
            }
        }

        // Final row with add button
        item {
            Row(Modifier.fillMaxWidth().testTag(NOTES_TABLE_ROW_TEST_TAG)) {
                TextButton(
                    onClick = {
                        val onEnterAction = {
                            if (tableData?.get(tableData!!.size)?.get(0)?.state?.value?.isNotEmpty() == true) {
                                tableData?.get(tableData!!.size)?.get(0)?.isFilled?.value ?: true
                            }
                        }
                        val keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                        val keyboardActions = KeyboardActions(onDone = { onEnterAction() })

                        val newNoteId = User.courses.getById(courseId)?.notes?.findNextID() ?: -1
                        val newRow = arrayOf(
                            NoteTableCell(
                                id = newNoteId,
                                state = mutableStateOf(""),
                                weight = noteCellWeight,
                                isFilled = mutableStateOf(false),
                                keyboardOptions = keyboardOptions,
                                keyboardActions = keyboardActions,
                            ),
                            NoteTableCell(
                                id = newNoteId,
                                state = mutableStateOf(dateFormat(getLocalDateTime())),
                                weight = modifiedCellWeight
                            ),
                            NoteTableCell(
                                id = newNoteId,
                                state = mutableStateOf(dateFormat(getLocalDateTime())),
                                weight = modifiedCellWeight
                            )
                        )
                        tableData = tableData?.toMutableList()?.plus(arrayOf(newRow))
                        AuthController.callRequest {
                            AuthController.updateCourses(
                                User.email,
                                User.courses.toMutableList()
                            )
                        }
                        navController.navigate(
                            Screen.RootMarkdownScreen.route.replace(
                                "{courseId}",
                                courseId.toString()
                            )
                        )
                    },
                    modifier = Modifier
                        .border(1.dp, Color.Black)
                        .fillMaxWidth()
                ) {
                    Text("+", style = ExtendedTheme.typography.tableBody)
                }
            }
        }
    }
}

@Composable
private fun RowScope.NoteTableStaticCell(
    text: MutableState<String>,
    weight: Float,
) {
    Text(
        text = text.value,
        modifier = Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .height(TABLE_ROW_HEIGHT)
            .padding(8.dp),
        style = ExtendedTheme.typography.tableBody,
    )

}

@Composable
private fun RowScope.NoteTableEditableCell(
    noteId: Int,
    courseId: Int,
    navController: NavController,
    text: MutableState<String>,
    weight: Float,
) {
    // Note name is saved, navigate to page on click
    TextButton(
        onClick = {
            AuthController.callRequest {
                AuthController.updateCourses(
                    User.email,
                    User.courses.toMutableList()
                )
            }
            navController.navigate(
                Screen.MarkdownScreen.route
                    .replace("{courseId}", courseId.toString())
                    .replace("{noteId}", noteId.toString())
            )
        },
        modifier = Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .height(TABLE_ROW_HEIGHT)
    ) {
        Text(text.value, style = ExtendedTheme.typography.tableBody)
    }
}

@Composable
private fun RowScope.HeadingCell(
    text: String,
    weight: Float,
) {
    Text(
        text = text,
        modifier = Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp),
        style = ExtendedTheme.typography.tableHeading,
    )
}
