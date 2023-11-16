package cs346.controller

import cs346.model.CourseCatalogue
import cs346.model.UWOpenAPI.UWOpenAPIClassSchedule
import cs346.model.UWOpenAPI.UWOpenAPICourse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

object UWOpenAPIController {
    private var client: HttpClient = HttpClient(CIO)
    private const val API_KEY = "17A589C203BC475489D5C9B2B671E4F0"
    private const val FALL_2023_TERM_CODE = "1239"

    fun callRequest(requestFunction: suspend () -> Unit) {
        runBlocking {
            launch {
                requestFunction()
            }
        }
    }

    suspend fun populateTermCourseData(termCode: String = FALL_2023_TERM_CODE): Boolean {
        val response = client.request("https://openapi.data.uwaterloo.ca/v3/Courses/$termCode") {
            headers {
                append("x-api-key", API_KEY)
            }
        }
        if (response.status == io.ktor.http.HttpStatusCode.OK) {
            val stringResponse: String = response.body()
            val courseObjects = Json.decodeFromString<List<UWOpenAPICourse>>(stringResponse)
            CourseCatalogue.initializeCourses(courseObjects)
            return true
        } else {
            println("Error: ${response.status}")
            return false
        }
    }

    suspend fun getCourseSchedule(termCode: String = FALL_2023_TERM_CODE, courseId: String): String {
        val response =
            client.request("https://openapi.data.uwaterloo.ca/v3/ClassSchedules/$termCode/$courseId") {
                headers {
                    append("x-api-key", API_KEY)
                }
            }
        if (response.status == io.ktor.http.HttpStatusCode.OK) {
            val stringResponse: String = response.body()
            val scheduleObjects = Json.decodeFromString<List<UWOpenAPIClassSchedule>>(stringResponse)
            val lectureInfo = if (scheduleObjects[0].scheduleData?.get(0)?.classMeetingDayPatternCode !== null) {
                val day = scheduleObjects[0].scheduleData?.get(0)?.classMeetingDayPatternCode
                val startTime =
                    scheduleObjects[0].scheduleData?.get(0)?.classMeetingStartTime?.split("T")?.get(1)?.substring(0, 5)
                val endTime =
                    scheduleObjects[0].scheduleData?.get(0)?.classMeetingEndTime?.split("T")?.get(1)?.substring(0, 5)
                "$day $startTime - $endTime"
            } else {
                ""
            }

            return lectureInfo
        } else {
            println("Error: ${response.status}")
            return ""
        }
    }
}