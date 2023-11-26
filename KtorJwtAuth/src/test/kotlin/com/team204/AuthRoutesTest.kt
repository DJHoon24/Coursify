package com.team204

import com.team204.data.models.courses.Course
import com.team204.data.models.users.User
import com.team204.data.models.users.UserDataSource
import com.team204.data.requests.SignInAuthRequest
import com.team204.data.requests.SignUpAuthRequest
import com.team204.data.requests.UpdateCoursesRequest
import com.team204.data.responses.UserResponse
import com.team204.security.hashing.HashingService
import com.team204.security.hashing.SaltedHash
import com.team204.security.token.TokenClaim
import com.team204.security.token.TokenConfig
import com.team204.security.token.TokenService
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.testing.*
import io.ktor.util.*
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.serialization.json.Json
import kotlin.test.*

@OptIn(InternalAPI::class)
class AuthRoutesTest {
    private val mockUserDataSource = mockk<UserDataSource>()
    private val mockHashingService = mockk<HashingService>()
    private val mockTokenService = mockk<TokenService>()
    private val tokenConfig = mockk<TokenConfig>()

    @BeforeTest
    fun setup() {
        clearAllMocks()
    }

    @AfterTest
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `signUp - successful sign up`() = testApplication {
        // Arrange
        val testSignUpRequest = SignUpAuthRequest("test@example.com", "password123", "John", "Doe")  // Fill this with a test request
        val testSaltedHash = SaltedHash("hashedpassword", "salt")  // Fill this with a test salted hash
        coEvery { mockUserDataSource.getUserByEmail(any()) } returns null
        coEvery { mockHashingService.generateSaltedHash(any()) } returns testSaltedHash
        coEvery { mockUserDataSource.insertUser(any()) } returns true

        // Act
        val response = client.post("/signup") {
            contentType(ContentType.Application.Json)
            body = Json.encodeToString(SignUpAuthRequest.serializer(), testSignUpRequest)
        }

        // Assert
        assertEquals(HttpStatusCode.Conflict, response.status)
    }

    @Test
    fun `signIn - successful sign in`() = testApplication {
        // Arrange
        val testSignInRequest = SignInAuthRequest("test@example.com", "password123")  // Fill this with a test request
        val testUser = User(
            id = "1",
            email = "test@example.com",
            password = "hashedpassword",
            salt = "salt",
            firstName = "John",
            lastName = "Doe",
            courses = mutableListOf()
        )
        coEvery { mockUserDataSource.getUserByEmail(any()) } returns testUser
        coEvery { mockHashingService.verify(any(), any()) } returns true

        // Act
        val response = client.post("/signin") {
            contentType(ContentType.Application.Json)
            body = Json.encodeToString(SignInAuthRequest.serializer(), testSignInRequest)
        }

        // Assert
        assertEquals(HttpStatusCode.OK, response.status)
        val userResponse = Json.decodeFromString(UserResponse.serializer(), response.bodyAsText())
        assertEquals(testUser.firstName, userResponse.firstName)
        assertEquals(testUser.lastName, userResponse.lastName)
        assertEquals(testUser.email, userResponse.email)
    }

    @Test
    fun `updateCourses - successful update`() = testApplication {
        // Arrange
        val testUpdateCoursesRequest = UpdateCoursesRequest(
            email = "test@example.com",
            courses = mutableListOf(Course(
                 id= 1,
                 courseNumber="CS 204",
                 lectureInfo= "Lecture 1",
                 instructors= "instructor 1",
                 courseDescription= "This is a test course",
                 review= "This is a test review",
                 rating= 5,
                 notes = mutableListOf(),
                 assignments = mutableListOf(),
                 createdDate = "2021-04-01T00:00:00.000Z",
                 lastModifiedDate = "2021-04-01T00:00:00.000Z",
            ))
        )
        coEvery { mockUserDataSource.updateCourses(any(), any()) } returns true

        // Act
        val response = client.post("/updatecourses") {
            contentType(ContentType.Application.Json)
            body = Json.encodeToString(UpdateCoursesRequest.serializer(), testUpdateCoursesRequest)
        }

        // Assert
        assertEquals(HttpStatusCode.OK, response.status)
    }
}
