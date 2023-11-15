package cs346.model

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import cs346.sqldelight.Database
import java.io.File

object Db {
    val database: Database

    init {
        val userHome = System.getProperty("user.home")
        val documentsDirectory = "$userHome/Documents/Coursify"
        val directory = File(documentsDirectory)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:$documentsDirectory/coursify.db")
        Database.Schema.create(driver)

        database = Database(driver)

        // add a User if there are no users in the database with id=1
        val userQueries = database.userQueries
        if (userQueries.selectAllUsers().executeAsList().isEmpty()) {
            userQueries.insertUser(
                firstName = "Jane",
                lastName = "Doe",
                email = "",
                password = "",
            )
        }
    }
}