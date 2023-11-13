package cs346.model

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import cs346.sqldelight.Database

object Db {
    val database: Database

    init {
        val userHome = System.getProperty("user.home")
        val desktopDirectory = "$userHome/Desktop"
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:$desktopDirectory/coursify.db")
        Database.Schema.create(driver)

        database = Database(driver)

        // add a User if there are no users in the database with id=1
        val userQuries = database.userQueries
        if (userQuries.selectAllUsers().executeAsList().isEmpty()) {
            userQuries.insertUser(
                firstName = "Jane",
                lastName = "Doe",
                email = "",
                password = "",
            )
        }
    }
}