package xyz.demorgan

import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef

class MediaService {
    fun getActiveWindowTitle(): String? {
        val user32 = User32.INSTANCE
        val hWnd: WinDef.HWND = user32.GetForegroundWindow()
        val windowText = CharArray(512)
        user32.GetWindowText(hWnd, windowText, 512)
        val title = String(windowText)
            .replace("\u0000", "")
            .trim()
        return title.ifEmpty { null }
    }
}