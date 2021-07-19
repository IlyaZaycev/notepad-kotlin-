package com.example.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.main.DataBase.MyAdapter
import com.example.notepad.DataBase.dbManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val DBmanager = dbManager(this)

    val myAdapter = MyAdapter(ArrayList(), this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        initSearchView()
    }

    override fun onResume() {
        super.onResume()
        DBmanager.openDB()
        adapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        DBmanager.colseDB()
    }

    fun onClickNew(view: View) {
        val i = Intent(this, MainActivity2::class.java)
        startActivity(i)
    }

    fun init() {
        rcView.layoutManager = LinearLayoutManager(this)
        val swapeHelper = getSwapMg()
        swapeHelper.attachToRecyclerView(rcView)
        rcView.adapter = myAdapter
    }

    fun initSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean { //Каждый раз происходит обновление при изменении поля
                val list = DBmanager.readDBData(newText!!)
                myAdapter.updateAdapter(list)
                //Log.d("MyLog", "New Next: $newText")
                return true
            }
        })
    }

    fun adapter() {
        val list = DBmanager.readDBData("")
        myAdapter.updateAdapter(list)
        if (list.size > 0) {
            tvNoElements.visibility = View.GONE
        } else {
            tvNoElements.visibility = View.VISIBLE
        }
    }

    //swape
    private fun getSwapMg(): ItemTouchHelper {
        return ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                myAdapter.removeItem(viewHolder.adapterPosition, DBmanager)
            }
        })
    }
}