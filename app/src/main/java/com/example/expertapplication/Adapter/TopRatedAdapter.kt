package com.example.expertapplication.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expertapplication.Models.TopRatedModel
import com.example.expertapplication.R

class TopRatedAdapter(private var context: Context, private val mList: List<TopRatedModel>) :
    RecyclerView.Adapter<TopRatedAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hometopratedlayout, parent, false)
        return ViewHolder(view)

    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.nameTextView.text = itemsViewModel.nametv
        holder.imageView2.setImageResource(itemsViewModel.image2)
        holder.imageView1.setImageResource(itemsViewModel.image1)
        holder.imageView3.setImageResource(itemsViewModel.image3)
        holder.expertTextView.text = itemsViewModel.experttv
        holder.costTextView.text = itemsViewModel.costtv
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.names)
        var imageView1: ImageView = itemView.findViewById(R.id.topratedpic)
        var imageView2: ImageView = itemView.findViewById(R.id.rates)
        var imageView3: ImageView = itemView.findViewById(R.id.messages)
        var expertTextView: TextView = itemView.findViewById(R.id.experttv)
        var costTextView: TextView = itemView.findViewById(R.id.costtv)
    }

}