package cs346.controller

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import cs346.model.UserPreferences
import cs346.model.UserTheme
import java.io.File

interface UserPreferencesController {
    fun loadPreferences(): UserPreferences
    fun savePreferences(preferences: UserPreferences)
}

// TODO: save the user preferences in db instead of local file
class FileUserPreferencesController(private val file: File) : UserPreferencesController {
    override fun loadPreferences(): UserPreferences {
        return if (file.exists()) {
            file.readText().let {
                val parts = it.split(",")
                UserPreferences(
                    windowWidth = parts[0].toFloat().dp,
                    windowHeight = parts[1].toFloat().dp,
                    placement = WindowPlacement.valueOf(parts[2]),
                    isMinimized = parts[3].toBoolean(),
                    positionX = parts[4].toFloat().dp,
                    positionY = parts[5].toFloat().dp,
                    timeFormat24H = parts[6].toBoolean(),
                    userTheme = UserTheme.valueOf(parts[7])
//                    token = parts[8]
                )
            }
        } else {
            UserPreferences()
        }
    }

    override fun savePreferences(preferences: UserPreferences) {
        file.writeText("${preferences.windowWidth.value},${preferences.windowHeight.value},${preferences.placement.name},${preferences.isMinimized},${preferences.positionX.value},${preferences.positionY.value},${preferences.timeFormat24H},${preferences.userTheme.name},${preferences.token}")
    }
}
