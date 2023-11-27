package cs346.views.theme

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat

fun dateFormat(localDateTime: LocalDateTime): String {
    val month = localDateTime.month.name.take(3)
    val day = localDateTime.dayOfMonth
    val year = localDateTime.year
    val hour = if (localDateTime.hour > 12) localDateTime.hour - 12 else localDateTime.hour
    val minute = localDateTime.minute.toString().padStart(2, '0')
    val amPm = if (localDateTime.hour >= 12) "PM" else "AM"
    return "$month. $day, $year, $hour:$minute $amPm"
}

fun getLocalDateTime(): LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.UTC)
}

fun convertLectureInfoTo24HourFormat(input: String): String {
    return try {
        val inputFormat = SimpleDateFormat("hh:mma")
        val outputFormat = SimpleDateFormat("HH:mm")

        val date = inputFormat.parse(input)

        outputFormat.format(date)
    } catch (e: Exception) {
        input
    }
}