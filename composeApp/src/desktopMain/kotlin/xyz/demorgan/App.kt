package xyz.demorgan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun App() {
    var windowTitle by remember { mutableStateOf("") }
    var processId by remember { mutableStateOf<Int?>(null) }
    var processName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        var lastTitle = ""
        var lastProcessId: Int? = null
        var lastProcessName: String? = null
        while (true) {
            val currentTitle = MediaService().getActiveWindowTitle()?.replace("\u0000", "")?.trim() ?: ""
            if (currentTitle != lastTitle) {
                windowTitle = currentTitle
                lastTitle = currentTitle
            }
            val currentProcessId = MediaService().getActiveProcessId()
            if (currentProcessId != lastProcessId) {
                processId = currentProcessId
                lastProcessId = currentProcessId
            }
            val currentProcessName = currentProcessId?.let {
                MediaService().getProcessNameById(it)
            }
            if (currentProcessName != lastProcessName) {
                processName = currentProcessName
                lastProcessName = currentProcessName
            }

            delay(1000)
        }
    }

    Column {
        Text(
            text = "Active window now is: $windowTitle",
            style = TextStyle(fontFamily = FontFamily.SansSerif)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "Process ID: $processId",
            style = TextStyle(fontFamily = FontFamily.SansSerif)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "Process name by id : $processName",
            style = TextStyle(fontFamily = FontFamily.SansSerif)
        )
    }
}