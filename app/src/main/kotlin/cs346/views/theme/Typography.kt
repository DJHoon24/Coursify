package cs346.views.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp

val NotoSansMonoFamily = FontFamily(
    Font(resource = "font/notosansmono_extrabold.ttf", weight = FontWeight.ExtraBold),
    Font(resource = "font/notosansmono_bold.ttf", weight = FontWeight.Bold),
    Font(resource = "font/notosansmono_semibold.ttf", weight = FontWeight.SemiBold),
    Font(resource = "font/notosansmono_medium.ttf", weight = FontWeight.Medium),
    Font(resource = "font/notosansmono_regular.ttf", weight = FontWeight.Normal),
    Font(resource = "font/notosansmono_light.ttf", weight = FontWeight.Light),
    Font(resource = "font/notosansmono_extralight.ttf", weight = FontWeight.ExtraLight),
    Font(resource = "font/notosansmono_Thin.ttf", weight = FontWeight.Thin)
)

val MartianMonoFamily = FontFamily(
    Font(resource = "font/martianmono_extrabold.ttf", weight = FontWeight.ExtraBold),
    Font(resource = "font/martianmono_bold.ttf", weight = FontWeight.Bold),
    Font(resource = "font/martianmono_semibold.ttf", weight = FontWeight.SemiBold),
    Font(resource = "font/martianmono_medium.ttf", weight = FontWeight.Medium),
    Font(resource = "font/martianmono_regular.ttf", weight = FontWeight.Normal),
    Font(resource = "font/martianmono_light.ttf", weight = FontWeight.Light),
    Font(resource = "font/martianmono_extralight.ttf", weight = FontWeight.ExtraLight),
    Font(resource = "font/martianmono_thin.ttf", weight = FontWeight.Thin),
)

@Immutable
data class ExtendedTypography(
    val cardHeading: TextStyle,
    val cardSubheading: TextStyle,
    val noteTitle: TextStyle,
    val noteBody: TextStyle,
    val landingTitle: TextStyle,
)

val LocalExtendedTypography = staticCompositionLocalOf {
    ExtendedTypography(
        cardHeading = TextStyle(
            fontFamily = MartianMonoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
        ),
        cardSubheading = TextStyle(
            fontFamily = NotoSansMonoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        ),
        noteTitle = TextStyle(
            fontFamily = MartianMonoFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp,
            color = Color.White
        ),
        noteBody = TextStyle(
            fontFamily = NotoSansMonoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
        ),
        landingTitle = TextStyle(
            fontFamily = MartianMonoFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
        )
    )
}