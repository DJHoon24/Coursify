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
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.commons.codec.digest.DigestUtils

fun Route.signUp(
    hashingService: HashingService,
    userDataSource: UserDataSource
) {
    post("signup") {
        val request = call.receiveOrNull<SignUpAuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "Received Null")
            return@post
        }

        val areFieldsBlank = request.email.isBlank() || request.password.isBlank()
        if (areFieldsBlank) {
            call.respond(HttpStatusCode.Conflict, "Email or password is blank")
            return@post
        }
        val isPwTooShort = request.password.length < 8
        if (isPwTooShort) {
            call.respond(HttpStatusCode.Conflict, "Password must be 8 characters or longer")
            return@post
        }
        val userCheck = userDataSource.getUserByEmail(request.email)
        if (userCheck != null){
            call.respond(HttpStatusCode.Conflict, "Email already exists")
            return@post
        }

        val saltedHash = hashingService.generateSaltedHash(request.password)
        val user = User(
            email = request.email,
            password = saltedHash.hash,
            salt = saltedHash.salt,
            firstName = request.firstName,
            lastName = request.lastName,
            courses = mutableListOf<Course>()
        )
        val wasAcknowledged = userDataSource.insertUser(user)
        if(!wasAcknowledged)  {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.signIn(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    post("signin") {
        val request = call.receiveOrNull<SignInAuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val user = userDataSource.getUserByEmail(request.email)
        if (user == null) {
            call.respond(HttpStatusCode.Conflict, "Email does not exist")
            return@post
        }

        val isValidPassword = hashingService.verify(
            value = request.password,
            saltedHash = SaltedHash(
                hash = user.password,
                salt = user.salt
            )
        )
        if (!isValidPassword) {
            println("Entered hash: ${DigestUtils.sha256Hex("${user.salt}${request.password}")}, Hashed PW: ${user.password}")
            call.respond(HttpStatusCode.Conflict, "Invalid password")
            return@post
        }

        val token = tokenService.generate(
            config = tokenConfig,
            TokenClaim(
                name = "userId",
                value = user.id
            )
        )

        call.respond(
            status = HttpStatusCode.OK,
            message = UserResponse(
                token = token,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                courses = user.courses
            )
        )
    }
}

fun Route.updateCourses(
    userDataSource: UserDataSource
) {
    post("updatecourses") {
        val request = call.receiveOrNull<UpdateCoursesRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val wasAcknowledged = userDataSource.updateCourses(request.email, request.courses)
        if(!wasAcknowledged)  {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.authenticate() {
    authenticate {
        get("authenticate") {
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Route.getSecretInfo() {
    authenticate {
        get("secret") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            call.respond(HttpStatusCode.OK, "Your userId is $userId")
        }
    }
}