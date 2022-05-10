package com.example.expertapplication.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expertapplication.Adapter.MessagesAdapter
import com.example.expertapplication.Models.MessagesModel
import com.example.expertapplication.R

class MessagesFragment : Fragment() {
    private lateinit var messagesAdapter: MessagesAdapter
    private val messageModelClassdataList = ArrayList<MessagesModel>()
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
        val view = inflater.inflate(R.layout.fragment_messages, container, false)
        initialisation(view)
        messageAdapterFun()

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initialisation(view: View) {
        recyclerView = view.findViewById(R.id.messagerecyclerview)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun messageAdapterFun() {
        messagesAdapter = MessagesAdapter(
            requireActivity().applicationContext, messageModelClassdataList
        )


        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = messagesAdapter
        this.messageModelClassdataList.clear()

        var movie = MessagesModel(
            R.drawable.messagepic2,
            "dannylove@gmail.com", "08:43", "Danny Hopkins"
        )

        messageModelClassdataList.add(movie)
        movie = MessagesModel(
            R.drawable.messagepic,
            "Here is another tutorial,if you...", "Tue", "Jose Farmer"

        )

        messageModelClassdataList.add(movie)
        movie = MessagesModel(
            R.drawable.notificationpic,
            "dannylove@gmail.com", "01 feb", "Danny Hopkins"
        )
        messageModelClassdataList.add(movie)
        movie = MessagesModel(
            R.drawable.profileicon,
            "Here is another tutorial,if you...", "08:43", "Jose Farmer"

        )

        messageModelClassdataList.add(movie)
        messagesAdapter.notifyDataSetChanged()

    }
}


