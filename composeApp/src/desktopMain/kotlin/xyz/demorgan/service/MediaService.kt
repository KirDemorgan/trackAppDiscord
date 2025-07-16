package xyz.demorgan.service

import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.Psapi
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinNT
import com.sun.jna.ptr.IntByReference

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

fun getActiveProcessId(): Int? {
    val user32 = User32.INSTANCE
    val hWnd: WinDef.HWND = user32.GetForegroundWindow()
    val pidRef = IntByReference()
    user32.GetWindowThreadProcessId(hWnd, pidRef)
    return pidRef.value.takeIf { it != 0 }
}

fun getProcessNameById(pid: Int): String? {
    val process = Kernel32.INSTANCE.OpenProcess(
        WinNT.PROCESS_QUERY_INFORMATION or WinNT.PROCESS_VM_READ,
        false,
        pid
    )
    if (process == null) return null
    val exeName = CharArray(512)
    val len = Psapi.INSTANCE.GetModuleFileNameExW(process, null, exeName, 512)
    Kernel32.INSTANCE.CloseHandle(process)
    return if (len > 0) String(exeName, 0, len).split("\\").last() else null
}

//    fun sendNotification(title: String, message: String) {
//        if (SystemTray.isSupported()) {
//            val tray = SystemTray.getSystemTray()
//            val image = Toolkit.getDefaultToolkit().createImage("icon.png")
//            val trayIcon = TrayIcon(image, "App")
//            trayIcon.isImageAutoSize = true
//            trayIcon.toolTip = "App"
//            tray.add(trayIcon)
//            trayIcon.displayMessage(title, message, MessageType.INFO)
//            tray.remove(trayIcon)
//        }
//    }
