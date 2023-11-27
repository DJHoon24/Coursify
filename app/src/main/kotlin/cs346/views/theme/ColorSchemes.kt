package cs346.views.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object DefaultColorScheme : ColorScheme() {
    override val primary = Color(0xFFF99C9C)
    override val background = Color(0xFFE8E0DE)
    override val fadedBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF99C9C),
            Color(0xFFF99C9C),
            Color(0xFFA9A9A9)
        ),
    )
    override val landingBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF99C9C),
            Color(0xFF7368B9)
        ),
    )
    override val userBackground = Color(0xFFD8D8D8)
    override val pageBackground = Color(0xFFBCB3B0)
    override val componentAccent = Color(0xFFD9D9D9)
}

object MintColorScheme : ColorScheme() {
    override val primary = Color(0xFF98FFB0) // Mint Green
    override val background = Color(0xFFF2F2F2) // Light Grey
    override val fadedBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF98FFB0), // Mint Green
            Color(0xFF98FFB0), // Mint Green
            Color(0xFFC0C0C0)  // Silver
        ),
    )
    override val landingBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF98FFB0), // Mint Green
            Color(0xFF5CDB95)  // Pastel Green
        ),
    )
    override val userBackground = Color(0xFFE6E6E6) // Light Grey
    override val pageBackground = Color(0xFFE6E6E6) // Light Grey
    override val componentAccent = Color(0xFF808080) // Light Grey
}

object DeepBlueColorScheme : ColorScheme() {
    override val primary = Color(0xFF007ACC) // Deep Blue
    override val background = Color(0xFFEFEFEF) // Light Silver
    override val fadedBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF007ACC), // Deep Blue
            Color(0xFF007ACC), // Deep Blue
            Color(0xFFB0B0B0)  // Medium Gray
        ),
    )
    override val landingBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF007ACC), // Deep Blue
            Color(0xFF4DA8DA)  // Sky Blue
        ),
    )
    override val userBackground = Color(0xFFD1D1D1) // Medium Gray
    override val pageBackground = Color(0xFFD1D1D1) // Medium Gray
    override val componentAccent = Color(0xFF595959) // Dark Gray
}

object SunsetColorScheme : ColorScheme() {
    override val primary = Color(0xFFE86363) // Sunset Red
    override val background = Color(0xFFFFE6A2) // Pale Yellow
    override val fadedBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE86363), // Sunset Red
            Color(0xFFE86363), // Sunset Red
            Color(0xFFFFD180)  // Peach
        ),
    )
    override val landingBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE86363), // Sunset Red
            Color(0xFFFFB74D)  // Amber
        ),
    )
    override val userBackground = Color(0xFFEDD7C1) // Beige
    override val pageBackground = Color(0xFFEDD7C1) // Beige
    override val componentAccent = Color(0xFFD4AC0D) // Gold
}

object OceanColorScheme : ColorScheme() {
    override val primary = Color(0xFF2196F3) // Ocean Blue
    override val background = Color(0xFFBBDEFB) // Light Blue
    override val fadedBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF2196F3), // Ocean Blue
            Color(0xFF2196F3), // Ocean Blue
            Color(0xFF90CAF9)  // Sky Blue
        ),
    )
    override val landingBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF2196F3), // Ocean Blue
            Color(0xFF64B5F6)  // Dodger Blue
        ),
    )
    override val userBackground = Color(0xFFBDD8ED) // Teal Blue
    override val pageBackground = Color(0xFFBDD8ED) // Teal Blue
    override val componentAccent = Color(0xFF1565C0) // Dark Blue
}

object ForestColorScheme : ColorScheme() {
    override val primary = Color(0xFF4CAF50) // Forest Green
    override val background = Color(0xFFC8E6C9) // Pale Green
    override val fadedBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4CAF50), // Forest Green
            Color(0xFF4CAF50), // Forest Green
            Color(0xFFA5D6A7)  // Light Green
        ),
    )
    override val landingBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4CAF50), // Forest Green
            Color(0xFF81C784)  // Medium Sea Green
        ),
    )
    override val userBackground = Color(0xFFCCDBBA) // Medium Green
    override val pageBackground = Color(0xFFCCDBBA) // Medium Green
    override val componentAccent = Color(0xFF2E7D32) // Dark Green
}
