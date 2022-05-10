package com.example.expertapplication.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.example.expertapplication.Activities.CreateGigsActivity
import com.example.expertapplication.Adapter.CreateGigAdapter
import com.example.expertapplication.Models.CreateGigModel
import com.example.expertapplication.Models.GigAgainstUserIdApiModel
import com.example.expertapplication.R
import com.example.expertapplication.WebService.POSTService
import com.example.expertapplication.utilis.AppURL
import com.example.expertapplication.utilis.ShareMemory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject

class GigsFragment : Fragment(), POSTService.ResponseInterface {
    private lateinit var creatGigsAdapter: CreateGigAdapter
    private val creatGigsModelClassdataList = ArrayList<CreateGigModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var createGigBtn: View
    private lateinit var shareMemory: ShareMemory
    private var category = ""
    private var longitude = ""
    private var latitude = ""

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
        val view = inflater.inflate(R.layout.fragment_gigs, container, false)
        initialisation(view)
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initialisation(view: View) {
        recyclerView = view.findViewById(R.id.creategigs_recyclerview)
        createGigBtn = view.findViewById(R.id.creategigbtn)
        shareMemory = ShareMemory.mInstence
        shareMemory = ShareMemory.getmInstence()
        createGigBtn.setOnClickListener {
            activity?.let {
                val intent = Intent(it, CreateGigsActivity::class.java)
                it.startActivity(intent)
            }
        }
    }


    private fun gigAgainstUserIdFun() {
        val postService = POSTService(context, this)
        postService.getDataWithoutParams(
            AppURL.GIGAGAINSTUSERID_URL + "?user_id=" + shareMemory.userId + "&category=" + category + "&lat=" + latitude + "&long=" + longitude,
            Request.Method.GET,

            )
    }

    override fun getResponse(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getBoolean("status")
            if (status) {
                val gsonBuilder = GsonBuilder()
                val gson: Gson = gsonBuilder.create()
                val userData =
                    gson.fromJson(`object`.toString(), GigAgainstUserIdApiModel::class.java)
                gigAgainstUserIdadapter(userData.data)

            } else {
                val message = `object`.getString("message")
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    override fun finish() {
    }

    private fun gigAgainstUserIdadapter(data: List<GigAgainstUserIdApiModel.Data>) {
        val creatGigsAdapter = CreateGigAdapter(requireActivity().applicationContext, data)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = creatGigsAdapter
    }

    override fun onResume() {
        super.onResume()
        gigAgainstUserIdFun()
    }


}