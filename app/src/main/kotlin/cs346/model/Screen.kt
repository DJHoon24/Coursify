package cs346.model

enum class Screen(
        val route: String,
) {
    CourseListScreen(
            route = "CourseList",
    ),
    RootMarkdownScreen(
            route = "Markdown/{courseId}"
    ),
    MarkdownScreen(
            route = "Markdown/{courseId}/{noteId}",
    ),
    CourseScreen(
            route = "Course/{courseId}",
    ),
}