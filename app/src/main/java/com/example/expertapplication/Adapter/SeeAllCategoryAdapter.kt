package com.example.expertapplication.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.expertapplication.Activities.SeeAllCategoryActivity
import com.example.expertapplication.Models.GigCategorySearchFilterApiModel
import com.example.expertapplication.R

class SeeAllCategoryAdapter(
    private val mItemClickListener: SeeAllCategoryActivity,
    private var context: Context,
    private val mList: List<GigCategorySearchFilterApiModel.Data>
) :
    RecyclerView.Adapter<SeeAllCategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.seeallcategorylayout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        holder.nameTextView.text = itemsViewModel.categoryName
        Glide.with(context).load(itemsViewModel.categoryImage).into(holder.imageView1)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.name)
        var imageView1: ImageView = itemView.findViewById(R.id.photographer)
    }
}