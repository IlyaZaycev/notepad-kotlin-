package com.example.main.DataBase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.main.MainActivity2
import com.example.main.R
import com.example.notepad.DataBase.dbManager

class MyAdapter(listMain: ArrayList<listItem>, contextM: Context) :
    RecyclerView.Adapter<MyAdapter.MyHolder>() {
    var listArray = listMain
    var context = contextM

    class MyHolder(itemView: View, contextV: Context) : RecyclerView.ViewHolder(itemView) {
        val context = contextV
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)

        fun setData(item: listItem) {
            tvTitle.text = item.title
            itemView.setOnClickListener() {
                val intent = Intent(context, MainActivity2::class.java).apply {
                    putExtra(intentConstants.I_TITLE_KEY, item.title)
                    putExtra(intentConstants.I_DESC_KEY, item.desc)
                    putExtra(intentConstants.I_ID_KEY,item.id)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyHolder(inflater.inflate(R.layout.rc_item, parent, false), context)
    }

    override fun getItemCount(): Int {
        return listArray.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(listArray.get(position))
    }

    fun updateAdapter(listItems: List<listItem>) {
        listArray.clear()
        listArray.addAll(listItems)
        notifyDataSetChanged()
    }

    fun removeItem(pos: Int,dbManager: dbManager) {
        dbManager.removeItemFromDB(listArray[pos].id.toString())
        listArray.removeAt(pos)
        notifyItemRangeChanged(0, listArray.size)
        notifyItemRemoved(pos)

    }
}