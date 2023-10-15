package cs346.views.theme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

object BaseColorScheme {
    val PRIMARY = Color(0xFFF99C9C)
    val LIGHT_BACKGROUND = Color(0xFFE8E0DE)
}

@Immutable
data class ExtendedColors(
    val primary: Color,
    val background: Color,
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        primary = BaseColorScheme.PRIMARY,
        background = BaseColorScheme.LIGHT_BACKGROUND,
    )
}