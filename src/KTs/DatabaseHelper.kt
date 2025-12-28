package com.example.bravo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream

class DatabaseHelper(private val ctx: Context) : SQLiteOpenHelper(ctx, DB_NAME, null, 1) {

    companion object {
        private const val DB_NAME = "truth_or_dare.db"
    }

    private val dbPath = ctx.getDatabasePath(DB_NAME).path

    override fun onCreate(db: SQLiteDatabase?) {}
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    // Másolja az adatbázist az assets-ből, ha még nincs
    fun createDatabase() {
        try {
            val dbFile = ctx.getDatabasePath(DB_NAME)
            if (!dbFile.exists()) {
                if (!dbFile.parentFile.exists()) dbFile.parentFile.mkdirs()
                readableDatabase
                copyDatabase()
            }
        } catch (e: Exception) {
            e.printStackTrace()  // Ez kiírja a hibát a Logcat-be
        }
    }

    private fun copyDatabase() {
        ctx.assets.open(DB_NAME).use { input ->
            FileOutputStream(dbPath).use { output ->
                val buffer = ByteArray(1024)
                var length: Int
                while (input.read(buffer).also { length = it } > 0) {
                    output.write(buffer, 0, length)
                }
                output.flush()
            }
        }
    }

    fun getRandomChallenge(type: String, mode: String): String {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT content FROM challenges WHERE type=? AND mode=? ORDER BY RANDOM() LIMIT 1",
            arrayOf(type, mode)
        )

        var result = "Nincs találat!"
        if (cursor.moveToFirst()) {
            result = cursor.getString(0)
        }
        cursor.close()
        db.close()
        return result
    }
}
