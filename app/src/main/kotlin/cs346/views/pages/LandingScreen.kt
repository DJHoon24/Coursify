package cs346.views.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import cs346.controller.DefaultButton
import cs346.views.theme.ExtendedTheme
import cs346.views.theme.LocalExtendedColors
import cs346.views.theme.PADDING_MEDIUM

@Composable
fun LandingScreen(onNavigate: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = LocalExtendedColors.current.colorScheme.landingBackground)
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = "Coursify",
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(PADDING_MEDIUM),
                style = ExtendedTheme.typography.landingTitle,
                color = Color.White
            )
            Text(
                text = "The all-in-one note taking\napp to help you take\ncontrol of your semester",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = ExtendedTheme.typography.cardSubheading(false),
                color = Color.White,
                textAlign = TextAlign.Center
            )
            DefaultButton(
                "Get Started",
                onNavigate,
                Modifier.align(Alignment.CenterHorizontally).padding(PADDING_MEDIUM)
            )
        }

    }
}