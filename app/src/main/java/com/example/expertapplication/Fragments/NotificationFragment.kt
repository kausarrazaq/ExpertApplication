package com.example.expertapplication.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expertapplication.Adapter.NotificationAdapter
import com.example.expertapplication.Models.NotificationModel
import com.example.expertapplication.R

class NotificationFragment : Fragment() {
    private lateinit var notificationAdapter: NotificationAdapter
    private val notificationModelClassdataList = ArrayList<NotificationModel>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        initialisation(view)
        notificationAdapterFun()

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initialisation(view: View) {
        recyclerView = view.findViewById(R.id.notificationrecycler)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notificationAdapterFun() {
        notificationAdapter = NotificationAdapter(
            requireActivity().applicationContext, notificationModelClassdataList
        )


        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = notificationAdapter
        this.notificationModelClassdataList.clear()

        var movie = NotificationModel(
            R.drawable.profileicon,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "1m ago"
        )

        notificationModelClassdataList.add(movie)
        movie = NotificationModel(
            R.drawable.notificationpic,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "10m ago"

        )

        notificationModelClassdataList.add(movie)
        movie = NotificationModel(
            R.drawable.messagepic2,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "60m ago"
        )
        notificationModelClassdataList.add(movie)
        movie = NotificationModel(
            R.drawable.messagepic,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "19m ago"

        )

        notificationModelClassdataList.add(movie)
        notificationAdapter.notifyDataSetChanged()

    }
}

