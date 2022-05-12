package com.example.expertapplication.Activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.example.expertapplication.R
import com.example.expertapplication.Adapter.SeeAllCategoryAdapter
import com.example.expertapplication.Models.GigCategorySearchFilterApiModel
import com.example.expertapplication.Models.SeeAllCategoryModel
import com.example.expertapplication.WebService.POSTService
import com.example.expertapplication.utilis.AppURL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject

class SeeAllCategoryActivity : AppCompatActivity(), POSTService.ResponseInterface {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var seeAllCategoryAdapter: SeeAllCategoryAdapter
    private val seeallList = ArrayList<SeeAllCategoryModel>()
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all_category)
        idViews()
        seeAllCategoryApiCallingFun()
    }

    private fun idViews() {
        toolbar = findViewById(R.id.toolbar_actionbar7)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView = findViewById(R.id.seeallrecycler)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun seeAllCategoryApiCallingFun() {
        val postService = POSTService(this, this)
        postService.getDataWithoutParams(
            AppURL.GIGCATEGORIESFORSEARCHFILTER_URL,
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
                    gson.fromJson(`object`.toString(), GigCategorySearchFilterApiModel::class.java)
                categoryAdapter(userData.data)

            } else {
                val message = `object`.getString("message")
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun categoryAdapter(data: List<GigCategorySearchFilterApiModel.Data>) {
        seeAllCategoryAdapter = SeeAllCategoryAdapter(this, this, data)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = seeAllCategoryAdapter
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }


}
