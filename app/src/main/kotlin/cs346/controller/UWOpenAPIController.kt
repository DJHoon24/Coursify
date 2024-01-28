package cs346.controller

import cs346.model.CourseCatalogue
import cs346.model.UWOpenAPI.UWOpenAPIClassSchedule
import cs346.model.UWOpenAPI.UWOpenAPICourse
import cs346.model.UserPreferences
import io.github.cdimascio.dotenv.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

object UWOpenAPIController {
    private var client: HttpClient = HttpClient(CIO)
    private val currentDirectory = System.getProperty("user.dir")
    private val dotenv = Dotenv.configure().directory("${currentDirectory}/.env").load()
    private val API_KEY = dotenv["UW_OPEN_API_KEY"]
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
        }

        println("Error: ${response.status}")
        return false
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

                var startTime =
                    scheduleObjects[0].scheduleData?.get(0)?.classMeetingStartTime?.split("T")?.get(1)?.substring(0, 5)
                if (!UserPreferences.timeFormat24H.value) {
                    startTime = startTime?.let { UserPreferences.convertLectureInfoTo12HourFormat(it) }
                }

                var endTime =
                    scheduleObjects[0].scheduleData?.get(0)?.classMeetingEndTime?.split("T")?.get(1)?.substring(0, 5)
                if (!UserPreferences.timeFormat24H.value) {
                    endTime = endTime?.let { UserPreferences.convertLectureInfoTo12HourFormat(it) }
                }

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