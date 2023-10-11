/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package cs

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() = application {
    Window(
        title = "hi",
        onCloseRequest = ::exitApplication
    ) {
        Greeting("Compose")
    }
}

@Composable
fun Greeting(name: String) {
    Text("Hello $name!")
}
