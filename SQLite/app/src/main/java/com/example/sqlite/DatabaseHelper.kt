package com.example.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "contacts.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "contacts"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PHONE = "phone"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NAME TEXT, $COLUMN_PHONE TEXT)")
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Thêm dữ liệu
    fun insertContact(name: String, phone: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_PHONE, phone)

        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    // Cập nhật dữ liệu
    fun updateContact(name: String, phone: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_PHONE, phone)

        val result = db.update(TABLE_NAME, values, "$COLUMN_NAME=?", arrayOf(name))
        db.close()
        return result > 0
    }

    // Xóa dữ liệu
    fun deleteContact(name: String): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_NAME=?", arrayOf(name))
        db.close()
        return result > 0
    }

    // Lấy tất cả dữ liệu
    fun getAllContacts(): String {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val stringBuilder = StringBuilder()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val phone = cursor.getString(2)
                stringBuilder.append("ID: $id | Tên: $name | SĐT: $phone\n")
            } while (cursor.moveToNext())
        } else {
            stringBuilder.append("Không có dữ liệu.")
        }

        cursor.close()
        db.close()
        return stringBuilder.toString()
    }
}
