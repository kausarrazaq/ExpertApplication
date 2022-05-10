package com.example.expertapplication.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expertapplication.Models.NotificationModel
import com.example.expertapplication.R

class NotificationAdapter(
    private var context: Context,
    private val mList: List<NotificationModel>
) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notificationlayout, parent, false)
        return ViewHolder(view)

    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.nameTextView.text = itemsViewModel.detail
        holder.imageView1.setImageResource(itemsViewModel.image1)
        holder.timeTextView.text = itemsViewModel.time

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.detailtv)
        var imageView1: ImageView = itemView.findViewById(R.id.notificationimage)
        var timeTextView: TextView = itemView.findViewById(R.id.timetv)
    }
}