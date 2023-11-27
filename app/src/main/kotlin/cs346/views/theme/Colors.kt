package cs346.views.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import cs346.model.UserPreferences
import cs346.model.UserTheme

sealed class ColorScheme {
    abstract val primary: Color
    abstract val background: Color
    abstract val fadedBackground: Brush
    abstract val landingBackground: Brush
    abstract val userBackground: Color
    abstract val pageBackground: Color
    abstract val componentAccent: Color
}

@Immutable
data class ExtendedColors(
    val colorScheme: ColorScheme,
)


val LocalExtendedColors = staticCompositionLocalOf<ExtendedColors> {
    error("No ColorScheme provided")
}

val LocalUserTheme = staticCompositionLocalOf<UserTheme> {
    error("No UserTheme provided")
}

@Composable
fun getExtendedColors(userTheme: UserTheme): ExtendedColors {
    val colorScheme = when (userTheme) {
        UserTheme.Default -> DefaultColorScheme
        UserTheme.Mint -> MintColorScheme
        UserTheme.DeepBlue -> DeepBlueColorScheme
        UserTheme.Sunset -> SunsetColorScheme
        UserTheme.Ocean -> OceanColorScheme
        UserTheme.Forest -> ForestColorScheme
    }

    return ExtendedColors(colorScheme)
}

@Composable
fun ProvideColorScheme(userPreferences: UserPreferences, content: @Composable () -> Unit) {
    val colorScheme = when (userPreferences.userTheme) {
        UserTheme.Default -> DefaultColorScheme
        UserTheme.Mint -> MintColorScheme
        UserTheme.DeepBlue -> DeepBlueColorScheme
        UserTheme.Sunset -> SunsetColorScheme
        UserTheme.Ocean -> OceanColorScheme
        UserTheme.Forest -> ForestColorScheme
    }

    val observedUserTheme by rememberUpdatedState(newValue = userPreferences.userTheme)


    CompositionLocalProvider(
        LocalExtendedColors provides ExtendedColors(colorScheme),
        LocalUserTheme provides observedUserTheme
    ) {
        content()
    }
}