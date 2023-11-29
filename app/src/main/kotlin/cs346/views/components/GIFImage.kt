package cs346.views.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.*

@Composable
fun GifComposable(
    gifPath: String,
    totalFrames: Int,
) {
    var frame by remember { mutableStateOf(0) }
    var job by remember { mutableStateOf<Job?>(null) }

    DisposableEffect(Unit) {
        job = CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                delay(50)
                frame = (frame + 1) % totalFrames
            }
        }
        onDispose {
            job?.cancel()
        }
    }

    val updatedFrame by rememberUpdatedState(frame)
    val imageBitmap = painterResource("images/${gifPath}/${updatedFrame + 1}.jpg")

    GifImage(painter = imageBitmap, contentDescription = null)
}

@Composable
fun GifImage(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.Fit
    )
}
