package com.example.expertapplication.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expertapplication.Activities.MessageActivity
import com.example.expertapplication.Models.MessagesModel
import com.example.expertapplication.R

class MessagesAdapter(private var context: Context, private val mList: List<MessagesModel>) :
    RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.messagelayout, parent, false)
        return ViewHolder(view)

    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.nameTextView.text = itemsViewModel.name
        holder.imageView1.setImageResource(itemsViewModel.image1)
        holder.timeTextView.text = itemsViewModel.time
        holder.detailTextView.text = itemsViewModel.detail
        holder.itemView.setOnClickListener {
            context.let {
                val i = Intent(context, MessageActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(i)

            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.nametv)
        var imageView1: ImageView = itemView.findViewById(R.id.messagepic)
        var timeTextView: TextView = itemView.findViewById(R.id.timetextview)
        var detailTextView: TextView = itemView.findViewById(R.id.detailtextview)
    }
}