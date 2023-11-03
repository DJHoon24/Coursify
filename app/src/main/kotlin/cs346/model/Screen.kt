package cs346.model

enum class Screen(
        val route: String,
) {
    CourseListScreen(
            route = "CourseList",
    ),
    DefaultMarkdownScreen(
            route = "Markdown"
    ),
    MarkdownScreen(
            route = "Markdown/{noteId}",
    ),
    CourseScreen(
            route = "Course/{courseId}",
    ),
}