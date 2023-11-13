package cs346.model.api

import kotlinx.serialization.Serializable

@Serializable
data class UWOpenAPIClassSchedule(
    val courseId: String?,
    val courseOfferNumber: Int,
    val sessionCode: String?,
    val classSection: Int,
    val termCode: String?,
    val classNumber: Int,
    val courseComponent: String?,
    val associatedClassCode: Int,
    val maxEnrollmentCapacity: Int,
    val enrolledStudents: Int,
    val enrollConsentCode: String?,
    val enrollConsentDescription: String?,
    val dropConsentCode: String?,
    val dropConsentDescription: String?,
    val scheduleData: List<UWOpenAPIMeeting>?,
    val instructorData: List<UWOpenAPIInstructor>?
)

