package cs346.views.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp
import org.w3c.dom.Text

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
    val cardHeading: (bold: Boolean) -> TextStyle,
    val cardSubheading: (bold: Boolean) -> TextStyle,
    val tableHeading: TextStyle,
    val tableBody: TextStyle,
    val noteTitle: TextStyle,
    val noteBody: TextStyle,
    val landingTitle: TextStyle,
    val pageTitle: TextStyle,
    val pageSubheading: TextStyle,
    val courseSubheading: TextStyle,
    val courseBody: TextStyle,
    val sidebarNote: TextStyle,
    val sidebarCourse: TextStyle,
    val sidebarHeadingText: TextStyle,
)

val LocalExtendedTypography = staticCompositionLocalOf {
    ExtendedTypography(
        cardHeading = { bold ->
            TextStyle(
                fontFamily = MartianMonoFamily,
                fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
                fontSize = 18.sp,
            )
        },
        cardSubheading = { bold ->
            TextStyle(
                fontFamily = NotoSansMonoFamily,
                fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
                fontSize = 14.sp,
            )
        },
        tableHeading = TextStyle(
            fontFamily = MartianMonoFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
        ),
        tableBody = TextStyle(
            fontFamily = NotoSansMonoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Color.Black,
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
        ),
        pageTitle = TextStyle(
            fontFamily = MartianMonoFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 32.sp,
            color = Color.White
        ),
        pageSubheading = TextStyle(
            fontFamily = MartianMonoFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            color = Color.White
        ),
        courseSubheading = TextStyle(
            fontFamily = MartianMonoFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Color.White
        ),
        courseBody = TextStyle(
            fontFamily = NotoSansMonoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            color = Color.White
        ),
        sidebarCourse = TextStyle(
            fontFamily = MartianMonoFamily,
            fontWeight = FontWeight.Light,
            fontSize = 16.sp,
        ),
        sidebarNote = TextStyle(
            fontFamily = MartianMonoFamily,
            fontWeight = FontWeight.Light,
            fontSize = 13.sp,
        ),
        sidebarHeadingText = TextStyle(
            fontFamily = MartianMonoFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
        ),
    )
}