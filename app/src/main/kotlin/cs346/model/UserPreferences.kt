package cs346.model

import androidx.compose.ui.unit.*
import androidx.compose.ui.window.WindowPlacement

data class UserPreferences(
    var windowWidth: Dp = 800.dp,
    var windowHeight: Dp = 600.dp,
    var placement: WindowPlacement = WindowPlacement.Floating,
    var isMinimized: Boolean = false,
    var positionX: Dp = 100.dp,
    var positionY: Dp = 100.dp,
    var timeFormat24H: Boolean = false,
    var theme: Theme = Theme.Light,
)
