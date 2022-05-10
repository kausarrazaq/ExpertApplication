package com.example.expertapplication.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expertapplication.Models.ReviewsModel
import com.example.expertapplication.R

class ReviewsAdapter(private var context: Context, private val mList: List<ReviewsModel>) :
    RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layoutforreviews, parent, false)
        return ViewHolder(view)

    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.nameTextView.text = itemsViewModel.nametv
        holder.imageView1.setImageResource(itemsViewModel.star)
        holder.dateTextView.text = itemsViewModel.datetv
        holder.detailTextView.text = itemsViewModel.detailtv
        holder.commentTextView.text = itemsViewModel.commenttv
        holder.postedbyTextView.text = itemsViewModel.postedbytv
        holder.ratingTextView.text = itemsViewModel.ratingtv

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.nameTextview)
        var imageView1: ImageView = itemView.findViewById(R.id.star)
        var dateTextView: TextView = itemView.findViewById(R.id.datetv)
        var detailTextView: TextView = itemView.findViewById(R.id.detailreview)
        var commentTextView: TextView = itemView.findViewById(R.id.commenttv)
        var postedbyTextView: TextView = itemView.findViewById(R.id.postedbytv)
        var ratingTextView: TextView = itemView.findViewById(R.id.rating)
    }

}