package cs346.views.theme

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

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