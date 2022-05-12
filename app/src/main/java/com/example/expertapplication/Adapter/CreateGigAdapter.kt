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
import com.bumptech.glide.Glide
import com.example.expertapplication.Activities.UserProfileActivity
import com.example.expertapplication.Models.GigAgainstUserIdApiModel
import com.example.expertapplication.R

class CreateGigAdapter(
    private var context: Context,
    private val mList: List<GigAgainstUserIdApiModel.Data>
) :
    RecyclerView.Adapter<CreateGigAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.creategigslayout, parent, false)
        return ViewHolder(view)

    }

    @SuppressLint("ResourceAsColor", "CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.expertTextView.text = itemsViewModel.subCategory
        Glide.with(context).load(itemsViewModel.gigImage).into(holder.imageView1)
        holder.nameTextView.text=itemsViewModel.userName
        Glide.with(context).load(itemsViewModel.userImage).into(holder.profileimage)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, UserProfileActivity::class.java)
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.nametxtview)
        var imageView1: ImageView = itemView.findViewById(R.id.graphicpic)
        var expertTextView: TextView = itemView.findViewById(R.id.experttxtview)
        var profileimage: ImageView= itemView.findViewById(R.id.profilepic)

    }

}

