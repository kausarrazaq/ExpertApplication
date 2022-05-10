package com.example.expertapplication.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expertapplication.Models.PopularCategoryModel
import com.example.expertapplication.R

class PopularCategoryAdapter(
    private var context: Context,
    private val mList: List<PopularCategoryModel>
) :
    RecyclerView.Adapter<PopularCategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.homelayoutforpopularcategory, parent, false)
        return ViewHolder(view)

    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.nameTextView.text = itemsViewModel.category
        holder.imageView1.setImageResource(itemsViewModel.image1)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.name)
        var imageView1: ImageView = itemView.findViewById(R.id.photographer)
    }

}

