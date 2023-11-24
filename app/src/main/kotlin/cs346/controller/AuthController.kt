package cs346.controller

import cs346.model.*
import cs346.model.auth.requests.SignInAuthRequest
import cs346.model.auth.requests.SignUpAuthRequest
import cs346.model.auth.requests.UpdateCoursesRequest
import cs346.model.auth.responses.SignInAuthResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

object AuthController {
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    private val url = "https://team204-server-491123dd7a81.herokuapp.com"
    fun callRequest(requestFunction: suspend () -> Unit) {
        runBlocking {
            launch {
                requestFunction()
            }
        }
    }

    suspend fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): HttpResponse? {
        try {
            val response: HttpResponse = client.post("$url/signup") {
                contentType(ContentType.Application.Json)
                setBody(
                    SignUpAuthRequest(
                        email,
                        password,
                        firstName,
                        lastName
                    )
                )
            }
            if (response.status.isSuccess()) {
                println("Success")
                signIn(email, password)
            } else {
                println("Error: Received response status: ${response.status}")
            }
            return response
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return null
        }
    }

    suspend fun signIn(email: String, password: String): HttpResponse? {
        try {
            val httpResponse = client.post("$url/signin") {
                contentType(ContentType.Application.Json)
                setBody(
                    SignInAuthRequest(
                        email = email,
                        password = password
                    )
                )
            }

            if (httpResponse.status.isSuccess()) {
                println("Success")

                val response: SignInAuthResponse = httpResponse.body()
                User.firstName = response.firstName
                User.lastName = response.lastName
                User.email = response.email

                val responseCourses = response.courses
                for (course in responseCourses) {
                    val courseId = User.courses.findNextID()
                    User.courses.add(
                        course.courseNumber,
                        course.lectureInfo,
                        course.instructors,
                        course.courseDescription,
                        course.review,
                        course.rating,
                        course.createdDate,
                        course.lastModifiedDate
                    )
                    for (note in course.notes) {
                        User.courses.getById(courseId)?.notes?.addNote(
                            note.title,
                            note.content,
                            note.createdDateTime,
                            note.lastModifiedDateTime
                        )
                    }
                    for (assignment in course.assignments) {
                        User.courses.getById(courseId)?.assignments?.add(
                            assignment.name,
                            assignment.dueDate,
                            assignment.score,
                            assignment.weight,
                            assignment.createdDate,
                            assignment.lastModifiedDate
                        )
                    }
                }
                //          val updatedPreferences = UserPreferences().copy(token = response.token)
                //          userPrefController.savePreferences(updatedPreferences)
            } else {
                println("Error: Received response status: ${httpResponse.status}")
            }
            return httpResponse
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return null
        }
    }

    suspend fun updateCourses(email: String, courses: MutableList<Course>) {
        val req = UpdateCoursesRequest(
            email = email,
            courses = courses,
        )
        try {
            val response: HttpResponse = client.post("$url/updatecourses") {
                contentType(ContentType.Application.Json)
                setBody(req)
            }
            if (response.status.isSuccess()) {
                println("Success")
            } else {
                println("Error: Received response status: ${response.status}")
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}
//    suspend fun authenticate(): AuthResult<Unit> {
//        return try {
//            if (UserPreferences().token == null)
//                return AuthResult.Unauthorized()
//            val token = UserPreferences().token
//            api.authenticate("Bearer $token")
//            AuthResult.Authorized()
//        } catch (e: HttpException) {
//            if (e.code() == 401) {
//                AuthResult.Unauthorized()
//            } else {
//                AuthResult.UnknownError()
//            }
//        } catch (e: Exception) {
//            AuthResult.UnknownError()
//        }
//    }
