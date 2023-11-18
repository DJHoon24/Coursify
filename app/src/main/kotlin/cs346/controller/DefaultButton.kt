package cs346.controller

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DefaultButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, contentColor: Color = Color.White) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor,
            backgroundColor = Color.Transparent
        ),
        border = BorderStroke(1.dp, contentColor),
    ) {
        Text(text)
    }
}