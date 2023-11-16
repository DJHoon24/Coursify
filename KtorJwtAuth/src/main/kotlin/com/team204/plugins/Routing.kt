package com.team204.plugins

import com.team204.*
import com.team204.data.models.users.UserDataSource
import com.team204.security.hashing.HashingService
import com.team204.security.token.TokenConfig
import com.team204.security.token.TokenService
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    routing {
        signUp(hashingService, userDataSource)
        signIn(userDataSource, hashingService, tokenService, tokenConfig)
        updateCourses(userDataSource)
        authenticate()
        getSecretInfo()
    }
}
