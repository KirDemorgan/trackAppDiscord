package xyz.demorgan.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import xyz.demorgan.service.getActiveProcessId
import xyz.demorgan.service.getActiveWindowTitle
import xyz.demorgan.service.getProcessNameById
import xyz.demorgan.service.logWindowChange

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
            val currentTitle = getActiveWindowTitle()?.replace("\u0000", "")?.trim() ?: ""
            if (currentTitle != lastTitle) {
                windowTitle = currentTitle
                lastTitle = currentTitle
            }
            val currentProcessId = getActiveProcessId()
            if (currentProcessId != lastProcessId) {
                processId = currentProcessId
                lastProcessId = currentProcessId
            }
            val currentProcessName = currentProcessId?.let {
                getProcessNameById(it)
            }
            if (currentProcessName != lastProcessName) {
                processName = currentProcessName
                lastProcessName = currentProcessName
                logWindowChange(currentTitle, currentProcessId, currentProcessName)
            }

            delay(1000)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF22223B)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = 12.dp,
                backgroundColor = Color(0xFFF2E9E4),
                modifier = Modifier
                    .padding(32.dp)
                    .widthIn(max = 400.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = "Active window now is: $windowTitle",
                        style = MaterialTheme.typography.h6,
                        color = Color(0xFF4A4E69)
                    )
                    Text(
                        text = "Process ID: $processId",
                        style = MaterialTheme.typography.body1,
                        color = Color(0xFF22223B)
                    )
                    Text(
                        text = "Process name by id : $processName",
                        style = MaterialTheme.typography.body1,
                        color = Color(0xFF22223B)
                    )
                }
            }
        }
    }
}