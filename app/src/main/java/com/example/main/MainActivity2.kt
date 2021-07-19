package com.example.main

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.main.DataBase.intentConstants
import com.example.notepad.DataBase.dbManager
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    val DBmanager = dbManager(this)
    var id = 0
    var isEditState = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        getIntents()
    }

    override fun onResume() {
        super.onResume()
        DBmanager.openDB()
    }

    override fun onDestroy() {
        super.onDestroy()
        DBmanager.colseDB()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

        }
    }

    fun onClickSave(view: View) {
        val myTitle = edTitle.text.toString()
        val myDesc = edDesc.text.toString()
        if (myTitle != "" && myDesc != "") {
            if (isEditState) {
                DBmanager.updateItem(myTitle, myDesc, id)
                finish()
            } else {
                DBmanager.insertToDB(myTitle, myDesc)
            }
            finish()
        }

    }
    fun onEditeEnabled(view: View){
        edTitle.isEnabled=true
        edDesc.isEnabled = true
        fbEdit.visibility = View.GONE
    }
    fun getIntents() {
        fbEdit.visibility = View.GONE
        val i = intent
        if (i != null) {

            if (i.getStringExtra(intentConstants.I_TITLE_KEY) != "null") {
                isEditState = true
                edTitle.isEnabled=false
                edDesc.isEnabled = false
                fbEdit.visibility = View.VISIBLE
                edTitle.setText(i.getStringExtra(intentConstants.I_TITLE_KEY))
                edDesc.setText(i.getStringExtra(intentConstants.I_DESC_KEY))
                id = i.getIntExtra(intentConstants.I_ID_KEY, 0)
            }
        }
    }
}