package com.example.expertapplication.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.expertapplication.Activities.FilterActivity
import com.example.expertapplication.Models.GigCategorySearchFilterModel
import com.example.expertapplication.R


class FilterAdapter(
    private val mItemClickListener: FilterActivity,
    private var context: Context,
    private val mList: List<GigCategorySearchFilterModel.Data>
) :
    RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    var selectedCategory: ArrayList<String> = ArrayList()

    interface ItemClickListener {
        fun onItemClick(category: String)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filterlayout, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor", "UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        val itemsViewModel = mList[position]
        holder.categoryTextView.text = itemsViewModel.category

        if (selectedCategory.contains(itemsViewModel.category)) {
            holder.cardView.background.setColorFilter(
                ContextCompat.getColor(context, R.color.black),
                PorterDuff.Mode.MULTIPLY
            )
            holder.categoryTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.imageview.background = (context.getDrawable(R.drawable.cross))
        } else {
            holder.cardView.background.setColorFilter(
                ContextCompat.getColor(context, R.color.white),
                PorterDuff.Mode.MULTIPLY
            )
            holder.categoryTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
            holder.imageview.background = (context.getDrawable(R.drawable.add))
        }
        holder.itemView.setOnClickListener { v ->

            if (selectedCategory.contains(itemsViewModel.category))
                selectedCategory.remove(itemsViewModel.category)
            else
                selectedCategory.add(itemsViewModel.category)

            notifyDataSetChanged()
        }
//        holder.itemView.setOnClickListener {
//            if (lastSelectedPosition == holder.position) {
////                lastSelectedPosition = RecyclerView.NO_POSITION
//                notifyDataSetChanged()
//                return@setOnClickListener
//            }

    }
    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var categoryTextView: TextView = itemView.findViewById(R.id.category)
        var imageview: ImageView = itemView.findViewById(R.id.addimage)
        var cardView: CardView = itemView.findViewById(R.id.card_view)
    }
}

