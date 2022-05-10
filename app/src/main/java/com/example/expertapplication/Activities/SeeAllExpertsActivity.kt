package com.example.expertapplication.Activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expertapplication.Adapter.SeeAllExpertsAdapter
import com.example.expertapplication.Models.SeeAllExpertsModel
import com.example.expertapplication.R

class SeeAllExpertsActivity : AppCompatActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var seeAllExpertsAdapter: SeeAllExpertsAdapter
    private val seeAllExpertsList = ArrayList<SeeAllExpertsModel>()
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all_experts)
        idViews()
        recyclerView()
    }

    private fun idViews() {
        recyclerView = findViewById(R.id.seeallexpertsrecycler)
        toolbar = findViewById(R.id.toolbar_actionbar9)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun recyclerView() {
        seeAllExpertsAdapter = SeeAllExpertsAdapter(seeAllExpertsList)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = seeAllExpertsAdapter

        var movie = SeeAllExpertsModel(
            R.drawable.topratedpic,
            "Isaac Ikram",
            "Photographer",
            "Cost $1000",
            R.drawable.ratingicon,
            R.drawable.messageicon
        )

        seeAllExpertsList.add(movie)
        movie = SeeAllExpertsModel(
            R.drawable.topratedpic2,
            "Robert A.Ockey", "Model", "Cost $1000", R.drawable.ratingicon, R.drawable.messageicon
        )

        seeAllExpertsList.add(movie)
        movie = SeeAllExpertsModel(
            R.drawable.topratedpic3,
            "Arnold W.Siegal",
            "Software Developer",
            "Cost $1000",
            R.drawable.ratingicon,
            R.drawable.messageicon
        )
        seeAllExpertsList.add(movie)
        movie = SeeAllExpertsModel(
            R.drawable.expertlawyer,
            "Scott Naramore", "Lawyer", "Cost $1000", R.drawable.ratingicon, R.drawable.messageicon
        )
        seeAllExpertsList.add(movie)

        movie = SeeAllExpertsModel(
            R.drawable.chef,
            "Jay B.Williams", "Chef", "Cost $1000", R.drawable.ratingicon, R.drawable.messageicon
        )
        seeAllExpertsList.add(movie)

        movie = SeeAllExpertsModel(
            R.drawable.doctor,
            "Ron Berman", "Doctor", "Cost $1000", R.drawable.ratingicon, R.drawable.messageicon
        )
        seeAllExpertsList.add(movie)

        seeAllExpertsAdapter.notifyDataSetChanged()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
