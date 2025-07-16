package xyz.demorgan.service

import java.sql.DriverManager

fun logWindowChange(title: String, processId: Int?, processName: String?) {
    ensureTableExists()
    val conn = DriverManager.getConnection("jdbc:sqlite:window_log.db")
    val stmt = conn.prepareStatement(
        "INSERT INTO window_changes (title, process_id, process_name, timestamp) VALUES (?, ?, ?, CURRENT_TIMESTAMP)"
    )
    stmt.setString(1, title)
    stmt.setObject(2, processId)
    stmt.setString(3, processName)
    stmt.executeUpdate()
    stmt.close()
    conn.close()
}


fun ensureTableExists() {
    val conn = DriverManager.getConnection("jdbc:sqlite:window_log.db")
    val stmt = conn.createStatement()
    stmt.executeUpdate(
        """
    CREATE TABLE IF NOT EXISTS window_changes (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        title TEXT,
        process_id INTEGER,
        process_name TEXT,
        timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
    )
    """.trimIndent()
    )
    stmt.close()
    conn.close()
}