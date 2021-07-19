package com.example.notepad.DataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dbCreator(context: Context) :
    SQLiteOpenHelper(context, DBClass.DATABASE_NAME, null, DBClass.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DBClass.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?. execSQL (DBClass.SQL_DELETE_TABLE)
        onCreate(db)
    }
}