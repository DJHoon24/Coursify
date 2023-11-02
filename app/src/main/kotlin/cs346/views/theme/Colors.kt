package cs346.views.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object BaseColorScheme {
    val PRIMARY = Color(0xFFF99C9C)
    val PAGE_BACKGROUND = Color(0xFFBCB3B0)
    val LIGHT_BACKGROUND = Color(0xFFE8E0DE)
    val FADED_BACKGROUND = Brush.verticalGradient(
            colors = listOf(
                    Color(0xFFF99C9C),
                    Color(0xFFF99C9C),
                    Color(0xFFA9A9A9)
            ),
    )
    val LANDING_BACKGROUND = Brush.verticalGradient(
            colors = listOf(
                    Color(0xFFF99C9C),
                    Color(0xFF7368B9)
            ),
    )
    val DEFAULT_USER_BACKGROUND = Color(0xFFD8D8D8)
}

@Immutable
data class ExtendedColors(
        val primary: Color,
        val background: Color,
        val fadedBackground: Brush,
        val landingBackground: Brush,
        val userBackground: Color,
        val pageBackground: Color,
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
            primary = BaseColorScheme.PRIMARY,
            background = BaseColorScheme.LIGHT_BACKGROUND,
            fadedBackground = BaseColorScheme.FADED_BACKGROUND,
            landingBackground = BaseColorScheme.LANDING_BACKGROUND,
            userBackground = BaseColorScheme.DEFAULT_USER_BACKGROUND,
            pageBackground = BaseColorScheme.PAGE_BACKGROUND,
    )
}