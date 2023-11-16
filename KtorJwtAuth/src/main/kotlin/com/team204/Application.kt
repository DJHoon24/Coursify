package com.team204

import com.team204.data.models.users.MongoUserDataSource
import io.ktor.server.application.*
import com.team204.plugins.*
import com.team204.security.hashing.SHA256HashingService
import com.team204.security.token.JwtTokenService
import com.team204.security.token.TokenConfig
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val mongoPw = "Q2EmQyT6THkC4hrB"
    val dbName = "team204DB"
    val db = KMongo.createClient(
        connectionString = "mongodb+srv://team204:$mongoPw@team204.pjzs6cm.mongodb.net/$dbName?retryWrites=true&w=majority"
    ).coroutine
        .getDatabase(dbName)
    val userDataSource = MongoUserDataSource(db)
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = "jwt-secret" 
    )
    val hashingService = SHA256HashingService()

    configureRouting(userDataSource, hashingService, tokenService, tokenConfig)
    configureSerialization()
    configureMonitoring()
    configureSecurity(tokenConfig)
}
