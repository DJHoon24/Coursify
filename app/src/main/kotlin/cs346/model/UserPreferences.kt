package cs346.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import cs346.views.theme.convertLectureInfoTo24HourFormat
import java.io.File
import java.text.SimpleDateFormat

object UserPreferences {
    var file: File
    var windowWidth: Dp = 1000.dp // Minimum value
    var windowHeight: Dp = 600.dp // Minimum value
    var placement: WindowPlacement = WindowPlacement.Floating
    var isMinimized: Boolean = false
    var positionX: Dp = 100.dp
    var positionY: Dp = 100.dp
    var timeFormat24H = mutableStateOf<Boolean>(true)


    private var _userTheme: MutableState<UserTheme>
    var userTheme: UserTheme
        get() = _userTheme.value
        set(value) {
            _userTheme.value = value
        }

    init {
        val userHome = System.getProperty("user.home")
        val documentsDirectory = "$userHome/Documents/Coursify"
        val directory = File(documentsDirectory)
        if (!directory.exists()) {
            directory.mkdirs()
        }

        file = File(documentsDirectory, "user-preferences.txt")
        if (file.length() == 0L) {
            file.createNewFile()
            file.writeText("${windowWidth.value},${windowHeight.value},${placement.name},${isMinimized},${positionX.value},${positionY.value},${timeFormat24H},${UserTheme.Default.name}")
        }

        file.readText().let {
            val parts = it.split(",")
            _userTheme = mutableStateOf(UserTheme.valueOf(parts[7]))
        }
    }

    fun loadPreferences() {
        if (file.exists()) {
            file.readText().let {
                val parts = it.split(",")
                windowWidth = parts[0].toFloat().dp
                windowHeight = parts[1].toFloat().dp
                placement = WindowPlacement.valueOf(parts[2])
                isMinimized = parts[3].toBoolean()
                positionX = parts[4].toFloat().dp
                positionY = parts[5].toFloat().dp
                timeFormat24H.value = parts[6].toBoolean()
                userTheme = UserTheme.valueOf(parts[7])
            }
        }
    }

    fun savePreferences(windowState: WindowState) {
        windowWidth = windowState.size.width
        windowHeight = windowState.size.height
        placement = windowState.placement
        isMinimized = windowState.isMinimized
        positionX = windowState.position.x
        positionY = windowState.position.y

        file.writeText("${windowWidth.value},${windowHeight.value},${placement.name},${isMinimized},${positionX.value},${positionY.value},${timeFormat24H},${userTheme.name}")
    }

    fun convertLectureInfoTo12HourFormat(input: String): String {
        return try {
            val inputFormat = SimpleDateFormat("HH:mm")
            val outputFormat = SimpleDateFormat("hh:mma")

            val date = inputFormat.parse(input)

            outputFormat.format(date)
        } catch (e: Exception) {
            input
        }
    }

    fun convertLectureInfo(scheduleTime: String, isTimeFormat24H: Boolean): String {
        val parts = scheduleTime.split(" ")

        if (parts.size == 4) {
            val dayCode = parts[0]
            val startTime = parts[1]
            val endTime = parts[3]

            val convertedStartTime = if (isTimeFormat24H) {
                convertLectureInfoTo24HourFormat(startTime)
            } else {
                convertLectureInfoTo12HourFormat(startTime)
            }

            val convertedEndTime = if (isTimeFormat24H) {
                convertLectureInfoTo24HourFormat(endTime)
            } else {
                convertLectureInfoTo12HourFormat(endTime)
            }


            return "$dayCode $convertedStartTime - $convertedEndTime"
        } else {
            // Handle invalid input
            return ""
        }
    }
}
