package xyz.demorgan

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import xyz.demorgan.pages.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "obsMusicTrack",
    ) {
        App()
    }
}