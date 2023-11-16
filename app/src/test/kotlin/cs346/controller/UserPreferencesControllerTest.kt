package cs346.controller

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import cs346.model.UserPreferences
import cs346.model.UserTheme
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.createTempDirectory
import kotlin.random.Random
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UserPreferencesControllerTest {

    private lateinit var testFile: File
    private lateinit var controller: FileUserPreferencesController

    @BeforeTest
    fun setUp() {
        val tempDir = createTempDirectory().toFile()
        testFile = File("${tempDir.absolutePath}/test-preferences.txt")
        controller = FileUserPreferencesController(testFile)
    }

    @AfterTest
    fun tearDown() {
        Path(testFile.toString()).toFile().delete()
    }

//    @Test
//    fun testLoadAndSaveDefaultPreferences() {
//        val expected = UserPreferences()
//        controller.savePreferences(expected)
//        val loaded = controller.loadPreferences()
//        assertEquals(expected, loaded)
//    }

    @Test
    fun testSaveOverridesPreviousPreferences() {
        val initialPreferences = UserPreferences()
        controller.savePreferences(initialPreferences)

        val randomWindowWidth = Random.nextInt(0, 1920).dp
        val randomWindowHeight = Random.nextInt(0, 1080).dp
        val randomPositionX = Random.nextInt(0, 1920).dp
        val randomPositionY = Random.nextInt(0, 1080).dp

        val modifiedPreferences = initialPreferences.copy(
            windowWidth = randomWindowWidth,
            windowHeight = randomWindowHeight,
            placement = WindowPlacement.Maximized,
            isMinimized = true,
            positionX = randomPositionX,
            positionY = randomPositionY,
            timeFormat24H = true,
            userTheme = UserTheme.Dark,
        )

        controller.savePreferences(modifiedPreferences)
        val loaded = controller.loadPreferences()
        assertEquals(modifiedPreferences, loaded)
    }
}
