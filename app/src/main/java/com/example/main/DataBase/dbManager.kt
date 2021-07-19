package com.example.notepad.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.main.DataBase.listItem

class dbManager(context: Context) {
    val dbCreator = dbCreator(context);//открытие
    var db: SQLiteDatabase? = null

    fun openDB() {
        db = dbCreator.writableDatabase
    }

    fun insertToDB(title: String, content: String) {
        val values = ContentValues().apply {
            put(DBClass.COLUMN_NAME_TITLE, title)
            put(DBClass.COLUMN_NAME_CONTENT, content)
        }
        db?.insert(DBClass.TABLE_NAME, null, values)
    }

    fun updateItem(title: String, content: String, id: Int) {
        val selection = BaseColumns._ID + "=$id"
        val values = ContentValues().apply {
            put(DBClass.COLUMN_NAME_TITLE, title)
            put(DBClass.COLUMN_NAME_CONTENT, content)
        }
        db?.update(DBClass.TABLE_NAME, values, selection, null)
    }

    fun removeItemFromDB(id: String) {
        val selection = BaseColumns._ID + "=$id"
        db?.delete(DBClass.TABLE_NAME, selection, null)
    }

    fun readDBData(searchText: String): ArrayList<listItem> {
        val dataList = ArrayList<listItem>()
        val selection = "${DBClass.COLUMN_NAME_TITLE} like ?"
        val cursor = db?.query(
            DBClass.TABLE_NAME, null, selection, arrayOf("%$searchText%"), null, null, null
        )
        with(cursor) {
            while (this?.moveToNext()!!) {
                val dataTitel = cursor?.getString(cursor.getColumnIndex(DBClass.COLUMN_NAME_TITLE))
                val dataContent =
                    cursor?.getString(cursor.getColumnIndex(DBClass.COLUMN_NAME_CONTENT))
                val dataID =
                    cursor?.getInt(cursor.getColumnIndex(BaseColumns._ID))
                var item = listItem()
                item.title = dataTitel.toString()
                item.desc = dataContent.toString()
                item.id = dataID!!
                dataList.add(item)
            }
        }
        cursor?.close()
        return dataList
    }

    fun colseDB() {
        dbCreator.close()
    }
}