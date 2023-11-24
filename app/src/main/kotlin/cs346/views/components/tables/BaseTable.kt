package cs346.views.components.tables

enum class TextType {
    FLOAT,
    STRING
}

data class HeadingCell(
    val title: String,
    val textType: TextType = TextType.STRING,
    val weight: Float,
)
