package com.example.expertapplication.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.expertapplication.Models.SearchexpertApiModel
import com.example.expertapplication.R

class SearchViewAdapter(private var context: Context,
                        private val mList: List<SearchexpertApiModel.Data>
) :
RecyclerView.Adapter<SearchViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.searchviewlayout, parent, false)
        return ViewHolder(view)

    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.nameTextView.text = itemsViewModel.name
        holder.category.text= itemsViewModel.category
        Glide.with(context).load(itemsViewModel.image).into(holder.imageView)
    }
    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.showtextview)
        var imageView: ImageView = itemView.findViewById(R.id.showimage)
        var category: TextView= itemView.findViewById(R.id.showcategory)
    }

}