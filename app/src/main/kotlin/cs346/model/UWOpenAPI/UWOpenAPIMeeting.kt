package cs346.model.UWOpenAPI

import kotlinx.serialization.Serializable

@Serializable
data class UWOpenAPIMeeting(
    val courseId: String?,
    val courseOfferNumber: Int,
    val sessionCode: String?,
    val classSection: Int,
    val termCode: String?,
    val classMeetingNumber: Int,
    val scheduleStartDate: String?,
    val scheduleEndDate: String?,
    val classMeetingStartTime: String?,
    val classMeetingEndTime: String?,
    val classMeetingDayPatternCode: String?,
    val classMeetingWeekPatternCode: String?,
    val locationName: String?
)