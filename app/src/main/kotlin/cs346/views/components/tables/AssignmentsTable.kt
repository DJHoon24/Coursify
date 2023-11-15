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
import cs346.helper.toClass
import cs346.model.*
import cs346.views.components.ASSIGNMENTS_TABLE_ROW_TEST_TAG
import cs346.views.components.ASSIGNMENT_DELETE_TEST_TAG
import cs346.views.components.TABLE_ROW_HEIGHT
import cs346.views.theme.ExtendedTheme
import cs346.views.theme.getLocalDateTime
import java.util.*

data class AssignmentTableCell(
    val assignmentId: Int,
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
fun AssignmentsTable(data: Array<Assignment>? = null, courseId: Int) {
    val nameCellWeight = 0.2f
    val dueDateCellWeight = 0.2f
    val scoreCellWeight = 0.1f
    val weightCellWeight = 0.2f
    val weightedMarkCellWeight = 0.2f
    val weightedDeleteCellWeight = 0.1f

    val headingCells = arrayOf(
        HeadingCell("Assignment", TextType.STRING, nameCellWeight),
        HeadingCell("Due Date", TextType.STRING, dueDateCellWeight),
        HeadingCell("Score", TextType.FLOAT, scoreCellWeight),
        HeadingCell("Weight (%)", TextType.FLOAT, weightCellWeight),
        HeadingCell("Weighted Mark", TextType.FLOAT, weightedMarkCellWeight),
        HeadingCell("Delete", TextType.STRING, weightedDeleteCellWeight),
    )

    val transformedRowData = data?.mapIndexed { i, it ->
        AssignmentTableRow(
            nameCell = AssignmentTableCell(it.id, i, mutableStateOf(it.name), TextType.STRING, nameCellWeight),
            dueDateCell = AssignmentTableCell(it.id, i, mutableStateOf(it.dueDate), TextType.STRING, dueDateCellWeight),
            scoreCell = AssignmentTableCell(
                it.id,
                i,
                mutableStateOf(it.score.toString()),
                TextType.FLOAT,
                scoreCellWeight
            ),
            weightCell = AssignmentTableCell(
                it.id,
                i,
                mutableStateOf(it.weight.toString()),
                TextType.FLOAT,
                weightCellWeight
            ),
            weightedMarkCell = AssignmentTableCell(
                it.id,
                i,
                mutableStateOf(it.weightedMark.toString()),
                TextType.FLOAT,
                weightedMarkCellWeight
            ),
        )
    }
    var tableData by remember { mutableStateOf(transformedRowData) }

    var weightedMark = 0f
    if (data != null) {
        for (i in 0 until data.size) {
            weightedMark += data[i].score * (data[i].weight / 100)
        }
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
        tableData?.let { it ->
            items(it.size) {
                Row(Modifier.fillMaxWidth().testTag(ASSIGNMENTS_TABLE_ROW_TEST_TAG)) {
                    val assignmentId = tableData!![it].nameCell.assignmentId

                    // Name Cell
                    AssignmentTableCellComponent(
                        AssignmentTableCell(
                            assignmentId,
                            it,
                            tableData!![it].nameCell.state,
                            tableData!![it].nameCell.textType,
                            tableData!![it].nameCell.weight,
                            onChange = ({ value, _ ->
                                User.courses.getById(courseId)?.assignments?.editName(value, assignmentId)
                            })
                        )
                    )

                    // Due Date Cell
                    AssignmentTableCellComponent(
                        AssignmentTableCell(
                            assignmentId,
                            it,
                            tableData!![it].dueDateCell.state,
                            tableData!![it].dueDateCell.textType,
                            tableData!![it].dueDateCell.weight,
                            onChange = ({ value, _ ->
                                User.courses.getById(courseId)?.assignments?.editDueDate(value, assignmentId)
                            })
                        )
                    )

                    // Score Cell
                    AssignmentTableCellComponent(
                        AssignmentTableCell(
                            assignmentId,
                            it,
                            tableData!![it].scoreCell.state,
                            tableData!![it].scoreCell.textType,
                            tableData!![it].scoreCell.weight,
                            onChange = ({ value, rowId ->
                                val mark = value.toFloatOrNull()
                                val weight = tableData!![rowId].weightCell.state.value.toFloatOrNull()
                                if (mark !== null && weight !== null) {
                                    val updatedRowWeightedMark = mark * (weight / 100)
                                    val prevWeightedMark =
                                        if (tableData!![rowId].weightedMarkCell.state.value.toFloatOrNull() !== null) {
                                            tableData!![rowId].weightedMarkCell.state.value.toFloat()
                                        } else {
                                            0f
                                        }

                                    totalMark = totalMark - prevWeightedMark + updatedRowWeightedMark
                                    tableData!![rowId].weightedMarkCell.state.value = updatedRowWeightedMark.toString()

                                    User.courses.getById(courseId)?.assignments?.editScore(mark, assignmentId)
                                    User.courses.getById(courseId)?.assignments?.editWeight(weight, assignmentId)
                                    User.courses.getById(courseId)?.assignments?.editWeightedMark(updatedRowWeightedMark, assignmentId)
                                }
                            })
                        )
                    )

                    // Weight Cell
                    AssignmentTableCellComponent(
                        AssignmentTableCell(
                            assignmentId,
                            it,
                            tableData!![it].weightCell.state,
                            tableData!![it].weightCell.textType,
                            tableData!![it].weightCell.weight,
                            onChange = ({ value, rowId ->
                                val mark = tableData!![rowId].scoreCell.state.value.toFloatOrNull()
                                val weight = value.toFloatOrNull()
                                if (mark !== null && weight !== null) {
                                    val updatedRowWeightedMark = mark * (weight / 100)
                                    val prevWeightedMark =
                                        if (tableData!![rowId].weightedMarkCell.state.value.toFloatOrNull() !== null) {
                                            tableData!![rowId].weightedMarkCell.state.value.toFloat()
                                        } else {
                                            0f
                                        }

                                    totalMark = totalMark - prevWeightedMark + updatedRowWeightedMark
                                    tableData!![rowId].weightedMarkCell.state.value = updatedRowWeightedMark.toString()

                                    User.courses.getById(courseId)?.assignments?.editScore(mark, assignmentId)
                                    User.courses.getById(courseId)?.assignments?.editWeight(weight, assignmentId)
                                    User.courses.getById(courseId)?.assignments?.editWeightedMark(updatedRowWeightedMark, assignmentId)
                                }
                            })
                        )
                    )

                    // Weighted Mark Cell
                    AssignmentTableCellComponent(
                        AssignmentTableCell(
                            assignmentId,
                            it,
                            tableData!![it].weightedMarkCell.state,
                            tableData!![it].weightedMarkCell.textType,
                            tableData!![it].weightedMarkCell.weight,
                            editable = false
                        )
                    )

                    TextButton(
                        onClick = {
                            User.courses.getById(courseId)?.assignments?.getById(assignmentId)?.deleteAssignment()
                            User.courses.getById(courseId)?.assignments?.remove(User.courses.getById(courseId)?.assignments?.getById(assignmentId))
                            tableData = tableData?.toMutableList()?.also { x ->
                                x.removeAt(it)
                            }
                            Db.database.assignmentQueries.deleteAssignment(assignmentId.toLong())
                        },
                        modifier = Modifier
                            .border(1.dp, Color.Black)
                            .weight(weightedDeleteCellWeight)
                            .testTag(ASSIGNMENT_DELETE_TEST_TAG)
                            .height(TABLE_ROW_HEIGHT)
                    ) {
                        Text("x", style = ExtendedTheme.typography.tableBody)
                    }
                }

            }
        }

        // Final row with add button and total mark
        item {
            Row(Modifier.fillMaxWidth().testTag(ASSIGNMENTS_TABLE_ROW_TEST_TAG)) {
                TextButton(
                    onClick = {
                        Db.database.transactionWithResult {
                            Db.database.assignmentQueries.insertAssignment(
                                courseId = courseId.toLong(),
                                name = "",
                                dueDate = "",
                                score = 0.0,
                                weight = 0.0,
                                weightedMark = 0.0,
                                createdDate = getLocalDateTime().toString(),
                                lastModifiedDate = getLocalDateTime().toString()
                            )
                            val lastInsertAssignment =
                                Db.database.assignmentQueries.lastInsertAssignment().executeAsOne()
                            val newAssignmentId = lastInsertAssignment.id.toInt()
                            val newRow = AssignmentTableRow(
                                nameCell = AssignmentTableCell(
                                    assignmentId = newAssignmentId,
                                    rowId = -1,
                                    textType = TextType.STRING,
                                    weight = nameCellWeight
                                ),
                                dueDateCell = AssignmentTableCell(
                                    assignmentId = newAssignmentId,
                                    rowId = -1,
                                    textType = TextType.STRING,
                                    weight = dueDateCellWeight
                                ),
                                scoreCell = AssignmentTableCell(
                                    assignmentId = newAssignmentId,
                                    rowId = -1,
                                    textType = TextType.FLOAT,
                                    weight = scoreCellWeight
                                ),
                                weightCell = AssignmentTableCell(
                                    assignmentId = newAssignmentId,
                                    rowId = -1,
                                    textType = TextType.FLOAT,
                                    weight = weightCellWeight
                                ),
                                weightedMarkCell = AssignmentTableCell(
                                    assignmentId = newAssignmentId,
                                    rowId = -1,
                                    textType = TextType.FLOAT,
                                    weight = weightedMarkCellWeight
                                ),
                            )
                            tableData = tableData?.toMutableList()?.plus(newRow)
                            User.courses.getById(courseId)?.assignments?.add(lastInsertAssignment.toClass())
                        }
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
            .height(TABLE_ROW_HEIGHT)
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