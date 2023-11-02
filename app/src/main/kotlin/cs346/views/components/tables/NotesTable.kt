package cs346.views.components.tables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
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
import cs346.model.Note
import cs346.views.components.NOTES_TABLE_ROW_TEST_TAG
import cs346.views.components.TABLE_ROW_HEIGHT
import cs346.views.theme.ExtendedTheme
import cs346.views.theme.dateFormat
import java.time.LocalDateTime

data class NoteTableCell(
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
fun NotesTable(data: Array<Note>) {
    val noteCellWeight = 0.6f
    val modifiedCellWeight = 0.2f
    val createdCellWeight = 0.2f

    val headingCells = arrayOf(
            HeadingCell("Note", TextType.STRING, noteCellWeight),
            HeadingCell("Modified", TextType.STRING, modifiedCellWeight),
            HeadingCell("Created", TextType.STRING, createdCellWeight)
    )

    val transformedRowData = data.map {
        arrayOf(
                NoteTableCell(mutableStateOf(it.title), noteCellWeight),
                NoteTableCell(mutableStateOf(dateFormat.format(it.lastModifiedDateTime)), modifiedCellWeight),
                NoteTableCell(mutableStateOf(dateFormat.format(it.createdDateTime)), createdCellWeight),
        )
    }
    var tableData by remember { mutableStateOf(transformedRowData) }

    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        // Header row
        stickyHeader {
            Row(Modifier.background(ExtendedTheme.colors.primary).testTag(NOTES_TABLE_ROW_TEST_TAG)) {
                headingCells.map {
                    HeadingCell(text = it.title, weight = it.weight)
                }
            }
        }

        // Core table rows
        items(tableData.size) {
            Row(Modifier.fillMaxWidth().testTag(NOTES_TABLE_ROW_TEST_TAG)) {
                // State for cell editability
                val onEnterAction = {
                    if (tableData[it][0].state.value.isNotEmpty()) {
                        tableData[it][0].isFilled.value = true
                    }
                }
                val keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                val keyboardActions = KeyboardActions(onDone = { onEnterAction() })

                NoteTableEditableCell(
                        tableData[it][0].state,
                        noteCellWeight,
                        tableData[it][0].isFilled,
                        keyboardOptions,
                        keyboardActions,
                )
                NoteTableStaticCell(tableData[it][1].state, modifiedCellWeight)
                NoteTableStaticCell(tableData[it][2].state, createdCellWeight)

//
//                tableData[it].map {
//
//
//                    NoteTableCellComponent(
//                            NoteTableCell(
//                                    state = it.state,
//                                    weight = it.weight,
//                            )
//                    )
//                }
            }
        }

        // Final row with add button
        item {
            Row(Modifier.fillMaxWidth().testTag(NOTES_TABLE_ROW_TEST_TAG)) {
                TextButton(
                        onClick = {
                            val onEnterAction = {
                                if (tableData[tableData.size][0].state.value.isNotEmpty()) {
                                    tableData[tableData.size][0].isFilled.value = true
                                }
                            }
                            val keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                            val keyboardActions = KeyboardActions(onDone = { onEnterAction() })

                            val newRow = arrayOf(
                                    NoteTableCell(
                                            state = mutableStateOf(""),
                                            weight = noteCellWeight,
                                            isFilled = mutableStateOf(false),
                                            keyboardOptions = keyboardOptions,
                                            keyboardActions = keyboardActions,
                                    ),
                                    NoteTableCell(
                                            state = mutableStateOf(dateFormat.format(LocalDateTime.now())),
                                            weight = modifiedCellWeight
                                    ),
                                    NoteTableCell(
                                            state = mutableStateOf(dateFormat.format(LocalDateTime.now())),
                                            weight = modifiedCellWeight
                                    )
                            )

//                            val newRow = headingCells.map {
//                                NoteTableCell(state = mutableStateOf(""), weight = it.weight)
//                            }.toTypedArray()
                            tableData = tableData.toMutableList().plus(arrayOf(newRow))
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
                    .height(40.dp)
                    .padding(8.dp),
            style = ExtendedTheme.typography.tableBody,
    )

}

@Composable
private fun RowScope.NoteTableEditableCell(
        text: MutableState<String>,
        weight: Float,
        isFilled: MutableState<Boolean>,
        keyboardOptions: KeyboardOptions,
        keyboardActions: KeyboardActions,
) {
    if (!isFilled.value) {
        BasicTextField(
                value = text.value,
                onValueChange = {
                    text.value = it
                },
                modifier = Modifier
                        .border(1.dp, Color.Black)
                        .weight(weight)
                        .height(TABLE_ROW_HEIGHT)
                        .padding(8.dp),
                textStyle = ExtendedTheme.typography.tableBody,
                singleLine = true,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
        )
    } else {
        TextButton(
                onClick = {

                },
                modifier = Modifier
                        .border(1.dp, Color.Black)
                        .weight(weight)
                        .height(TABLE_ROW_HEIGHT)
        ) {
            Text(text.value, style = ExtendedTheme.typography.tableBody)
        }
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
