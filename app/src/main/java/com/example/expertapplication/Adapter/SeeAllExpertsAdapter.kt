package com.example.expertapplication.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expertapplication.Activities.ProfileForCreateGigActivity
import com.example.expertapplication.Models.SeeAllExpertsModel
import com.example.expertapplication.R

class SeeAllExpertsAdapter(private val mList: List<SeeAllExpertsModel>) :
    RecyclerView.Adapter<SeeAllExpertsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.seeallexpertslayout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        holder.nameTextView.text = itemsViewModel.nametv
        holder.imageView2.setImageResource(itemsViewModel.image2)
        holder.imageView1.setImageResource(itemsViewModel.image1)
        holder.imageView3.setImageResource(itemsViewModel.image3)
        holder.expertTextView.text = itemsViewModel.experttv
        holder.costTextView.text = itemsViewModel.costtv
        holder.itemView.setOnClickListener {
            val intent = Intent(it.getContext(), ProfileForCreateGigActivity::class.java)
            it.getContext().startActivity(intent)
        }

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