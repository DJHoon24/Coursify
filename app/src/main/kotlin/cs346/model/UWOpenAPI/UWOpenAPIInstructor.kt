package cs346.model.UWOpenAPI

import kotlinx.serialization.Serializable

@Serializable
data class UWOpenAPIInstructor(
    val courseId: String?,
    val courseOfferNumber: Int,
    val sessionCode: String?,
    val classSection: Int,
    val termCode: String?,
    val instructorRoleCode: String?,
    val instructorFirstName: String?,
    val instructorLastName: String?,
    val instructorUniqueIdentifier: String?,
    val classMeetingNumber: Int
)