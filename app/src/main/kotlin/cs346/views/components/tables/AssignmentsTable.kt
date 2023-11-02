package cs346.views.components.tables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import cs346.model.Assignment
import cs346.views.components.ASSIGNMENTS_TABLE_ROW_TEST_TAG
import cs346.views.theme.ExtendedTheme
import cs346.views.theme.dateFormat
import java.util.*

data class AssignmentTableCell(
        val rowId: Int,
        val state: MutableState<String> = mutableStateOf(""),
        val textType: TextType,
        val weight: Float,
        val onChange: ((value: String, rowId: Int) -> Unit)? = null,
        val editable: Boolean = true,
)

data class AssignmentTableRow(
        val nameCell: AssignmentTableCell,
        val dueDateCell: AssignmentTableCell,
        val scoreCell: AssignmentTableCell,
        val weightCell: AssignmentTableCell,
        val weightedMarkCell: AssignmentTableCell,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AssignmentsTable(data: Array<Assignment>) {
    val nameCellWeight = 0.2f
    val dueDateCellWeight = 0.3f
    val scoreCellWeight = 0.1f
    val weightCellWeight = 0.2f
    val weightedMarkCellWeight = 0.2f

    val headingCells = arrayOf(
            HeadingCell("Assignment", TextType.STRING, nameCellWeight),
            HeadingCell("Due Date", TextType.STRING, dueDateCellWeight),
            HeadingCell("Score", TextType.FLOAT, scoreCellWeight),
            HeadingCell("Weight (%)", TextType.FLOAT, weightCellWeight),
            HeadingCell("Weighted Mark", TextType.FLOAT, weightedMarkCellWeight),
    )

    val transformedRowData = data.mapIndexed { i, it ->
        AssignmentTableRow(


                nameCell = AssignmentTableCell(i, mutableStateOf(it.name), TextType.STRING, nameCellWeight),
                dueDateCell = AssignmentTableCell(i, mutableStateOf(dateFormat.format(it.dueDate)), TextType.STRING, dueDateCellWeight),
                scoreCell = AssignmentTableCell(i, mutableStateOf(it.score.toString()), TextType.FLOAT, scoreCellWeight),
                weightCell = AssignmentTableCell(i, mutableStateOf(it.weight.toString()), TextType.FLOAT, weightCellWeight),
                weightedMarkCell = AssignmentTableCell(i, mutableStateOf(it.weightedMark.toString()), TextType.FLOAT, weightedMarkCellWeight),
        )
    }
    var tableData by remember { mutableStateOf(transformedRowData) }

    var weightedMark = 0f;
    for (i in 0 until data.size) {
        weightedMark += data[i].score * (data[i].weight / 100)
    }
    var totalMark by remember { mutableStateOf(weightedMark) }

    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        // Header row
        stickyHeader {
            Row(Modifier.background(ExtendedTheme.colors.primary).testTag(ASSIGNMENTS_TABLE_ROW_TEST_TAG)) {
                headingCells.map {
                    HeadingCell(text = it.title, weight = it.weight)
                }
            }
        }

        // Core table rows
        items(tableData.size) {
            Row(Modifier.fillMaxWidth().testTag(ASSIGNMENTS_TABLE_ROW_TEST_TAG)) {
                // Assignment and due date cells require no parameter overrides
                AssignmentTableCellComponent(
                        AssignmentTableCell(
                                it, tableData[it].nameCell.state, tableData[it].nameCell.textType, tableData[it].nameCell.weight
                        )

                )
                AssignmentTableCellComponent(
                        AssignmentTableCell(
                                it, tableData[it].dueDateCell.state, tableData[it].dueDateCell.textType, tableData[it].dueDateCell.weight
                        )
                )


                // Score, weight, and weighted mark cells require onChange functions
                AssignmentTableCellComponent(
                        AssignmentTableCell(
                                it, tableData[it].scoreCell.state, tableData[it].scoreCell.textType, tableData[it].scoreCell.weight,
                                onChange = ({ value, rowId ->
                                    val mark = value.toFloatOrNull()
                                    val weight = tableData[rowId].weightCell.state.value.toFloatOrNull()
                                    if (mark !== null && weight !== null) {
                                        val updatedRowWeightedMark = mark * (weight / 100)
                                        val prevWeightedMark = if (tableData[rowId].weightedMarkCell.state.value.toFloatOrNull() !== null) {
                                            tableData[rowId].weightedMarkCell.state.value.toFloat()
                                        } else {
                                            0f
                                        }

                                        totalMark = totalMark - prevWeightedMark + updatedRowWeightedMark
                                        tableData[rowId].weightedMarkCell.state.value = updatedRowWeightedMark.toString()
                                    }
                                })
                        )
                )

                AssignmentTableCellComponent(
                        AssignmentTableCell(
                                it, tableData[it].weightCell.state, tableData[it].weightCell.textType, tableData[it].weightCell.weight,
                                onChange = ({ value, rowId ->
                                    val mark = tableData[rowId].scoreCell.state.value.toFloatOrNull()
                                    val weight = value.toFloatOrNull()
                                    if (mark !== null && weight !== null) {
                                        val updatedRowWeightedMark = mark * (weight / 100)
                                        val prevWeightedMark = if (tableData[rowId].weightedMarkCell.state.value.toFloatOrNull() !== null) {
                                            tableData[rowId].weightedMarkCell.state.value.toFloat()
                                        } else {
                                            0f
                                        }

                                        totalMark = totalMark - prevWeightedMark + updatedRowWeightedMark
                                        tableData[rowId].weightedMarkCell.state.value = updatedRowWeightedMark.toString()
                                    }
                                })
                        )
                )

                AssignmentTableCellComponent(
                        AssignmentTableCell(
                                it, tableData[it].weightedMarkCell.state, tableData[it].weightedMarkCell.textType, tableData[it].weightedMarkCell.weight, editable = false
                        )
                )
            }

        }

        // Final row with add button and total mark
        item {
            Row(Modifier.fillMaxWidth().testTag(ASSIGNMENTS_TABLE_ROW_TEST_TAG)) {
                TextButton(
                        onClick = {
                            val newRow = AssignmentTableRow(
                                    nameCell = AssignmentTableCell(rowId = -1, textType = TextType.STRING, weight = nameCellWeight),
                                    dueDateCell = AssignmentTableCell(rowId = -1, textType = TextType.STRING, weight = dueDateCellWeight),
                                    scoreCell = AssignmentTableCell(rowId = -1, textType = TextType.FLOAT, weight = scoreCellWeight),
                                    weightCell = AssignmentTableCell(rowId = -1, textType = TextType.FLOAT, weight = weightCellWeight),
                                    weightedMarkCell = AssignmentTableCell(rowId = -1, textType = TextType.FLOAT, weight = weightedMarkCellWeight),
                            )
                            tableData = tableData.toMutableList().plus(newRow)
                        },
                        modifier = Modifier
                                .border(1.dp, Color.Black)
                                .fillMaxWidth()
                ) {
                    Text("+", style = ExtendedTheme.typography.tableBody)
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(
                        text = String.format(Locale.US, "%.2f", totalMark),
                        style = ExtendedTheme.typography.tableHeading,
                        modifier = Modifier.padding(8.dp)
                )
            }
        }
    }

}

@Composable
private fun RowScope.AssignmentTableCellComponent(
        args: AssignmentTableCell
) {
    BasicTextField(
            enabled = args.editable,
            value = args.state.value,
            onValueChange = {
                if (args.textType === TextType.STRING || it.isEmpty()) {
                    args.state.value = it
                } else if (args.textType === TextType.FLOAT && it.toFloatOrNull() !== null) {
                    args.state.value = it
                }
                args.onChange?.invoke(it, args.rowId)
            },
            modifier = Modifier
                    .border(1.dp, Color.Black)
                    .weight(args.weight)
                    .padding(8.dp),
            textStyle = ExtendedTheme.typography.tableBody,
            singleLine = true
    )
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