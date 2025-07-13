package xyz.demorgan

import androidx.compose.runtime.*
import androidx.compose.material.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import kotlinx.coroutines.delay

@Composable
fun App() {
    var windowTitle by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        var lastTitle = ""
        while (true) {
            val currentTitle = MediaService().getActiveWindowTitle()?.replace("\u0000", "")?.trim() ?: ""
            if (currentTitle != lastTitle) {
                windowTitle = currentTitle
                lastTitle = currentTitle
            }
            delay(1000)
        }
    }

    Text(
        text = "Main window now is: $windowTitle",
        style = TextStyle(fontFamily = FontFamily.SansSerif)
    )
}