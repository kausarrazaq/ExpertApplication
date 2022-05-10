package com.example.expertapplication.Fragments

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.example.expertapplication.Activities.FilterActivity
import com.example.expertapplication.Activities.SeeAllCategoryActivity
import com.example.expertapplication.Activities.SeeAllExpertsActivity
import com.example.expertapplication.Adapter.SearchViewAdapter
import com.example.expertapplication.Adapter.PopularCategoryAdapter
import com.example.expertapplication.Adapter.TopRatedAdapter
import com.example.expertapplication.Models.PopularCategoryModel
import com.example.expertapplication.Models.SearchexpertApiModel
import com.example.expertapplication.Models.TopRatedModel
import com.example.expertapplication.R
import com.example.expertapplication.WebService.POSTService
import com.example.expertapplication.utilis.AppURL
import com.example.expertapplication.utilis.ShareMemory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject


class HomeFragment : Fragment(), POSTService.ResponseInterface {
    private lateinit var homeAdapter: PopularCategoryAdapter
    private val homeModelClassdataList = ArrayList<PopularCategoryModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var seeAllTextView: View
    private lateinit var filterIcon: ImageView
    private lateinit var seeAllExpertsTv: View
    private lateinit var seeAllExperts: TextView
    private lateinit var seeAllCategories: TextView
    private lateinit var topRatedTv: TextView
    private lateinit var topCategoryTv: TextView
    private var topRatedModelClassdataList = ArrayList<TopRatedModel>()
    private lateinit var topRatedAdapter: TopRatedAdapter
    private lateinit var recyclerViewForTopRated: RecyclerView
    private lateinit var searchViewAdapter: SearchViewAdapter
    private lateinit var hideRecyclerView: RecyclerView
    private lateinit var shareMemory: ShareMemory
    private lateinit var searchView: EditText
    private var category = ""
    private var longitude = ""
    private var latitude = ""
    private var searchList: ArrayList<SearchexpertApiModel.Data> = ArrayList()
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
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initialisation(view)
        homeAdapterFun()
        topRatesAdapterFun()
        searchListeningFun()
        visibilityOfViewsFun()
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initialisation(view: View) {
        recyclerView = view.findViewById(R.id.home_recyclerview)
        recyclerViewForTopRated = view.findViewById(R.id.topratedrecyclerview)
        seeAllTextView = view.findViewById(R.id.seeallview)
        filterIcon = view.findViewById(R.id.filter)
        seeAllExpertsTv = view.findViewById(R.id.seeallexpertsview)
        shareMemory = ShareMemory.mInstence
        shareMemory = ShareMemory.getmInstence()
        searchView = view.findViewById(R.id.searchView)
        hideRecyclerView = view.findViewById(R.id.hiderecyclerview)
        seeAllExperts = view.findViewById(R.id.seeallexperts)
        seeAllCategories = view.findViewById(R.id.seeallTV)
        topCategoryTv = view.findViewById(R.id.signupview)
        topRatedTv = view.findViewById(R.id.topratedtv)


        seeAllExpertsTv.setOnClickListener {
            activity?.let {
                val intent = Intent(it, SeeAllExpertsActivity::class.java)
                it.startActivity(intent)
            }
        }
        filterIcon.setOnClickListener {
            activity?.let {
                val intent = Intent(it, FilterActivity::class.java)
                startActivityForResult(intent, 1)
            }
        }
        seeAllTextView.setOnClickListener {
            activity?.let {
                val intent = Intent(it, SeeAllCategoryActivity::class.java)
                it.startActivity(intent)
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun homeAdapterFun() {
        homeAdapter = PopularCategoryAdapter(
            requireActivity().applicationContext, homeModelClassdataList
        )


        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = homeAdapter
        this.homeModelClassdataList.clear()

        var movie = PopularCategoryModel(
            R.drawable.photographer,
            "Photographer",
        )

        homeModelClassdataList.add(movie)
        movie = PopularCategoryModel(
            R.drawable.lawwyer,
            "lawyer",

            )


        homeModelClassdataList.add(movie)
        homeAdapter.notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun topRatesAdapterFun() {
        topRatedAdapter = TopRatedAdapter(
            requireActivity().applicationContext, topRatedModelClassdataList
        )


        val layoutManager = GridLayoutManager(context, 2)
        recyclerViewForTopRated.layoutManager = layoutManager
        recyclerViewForTopRated.adapter = topRatedAdapter
        this.topRatedModelClassdataList.clear()

        var movie = TopRatedModel(
            R.drawable.topratedpic,
            "Isaac Ikram",
            "Photographer",
            "Cost $1000",
            R.drawable.ratingicon,
            R.drawable.messageicon
        )

        topRatedModelClassdataList.add(movie)
        movie = TopRatedModel(
            R.drawable.topratedpic2,
            "Robert A.Ockey", "Model", "Cost $1000", R.drawable.ratingicon, R.drawable.messageicon
        )

        topRatedModelClassdataList.add(movie)
        movie = TopRatedModel(
            R.drawable.topratedpic3,
            "Arnold W.Siegal",
            "Software Developer",
            "Cost $1000",
            R.drawable.ratingicon,
            R.drawable.messageicon
        )
        topRatedModelClassdataList.add(movie)
        movie = TopRatedModel(
            R.drawable.expertlawyer,
            "Scott Naramore", "Lawyer", "Cost $1000", R.drawable.ratingicon, R.drawable.messageicon
        )
        topRatedModelClassdataList.add(movie)

        topRatedAdapter.notifyDataSetChanged()

    }

    private fun searchApiFun() {
        val name = searchView.text.toString()
        val postService = POSTService(context, this)
        postService.getDataWithoutParams(
            AppURL.SEARCHEXPERT_URL + "?id=" + shareMemory.userId + "?name=" + name + "&category=" + category + "&lat="
                    + latitude + "&long=" + longitude,
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
                val userData = gson.fromJson(`object`.toString(), SearchexpertApiModel::class.java)
                searchList = userData.data as ArrayList<SearchexpertApiModel.Data>
                searchViewAdapter(searchList)

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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {

                    val yourBool: Boolean = data?.extras!!.getBoolean("IsChecked")
                    if (!yourBool) {
                        category = data.getStringExtra("categoryName").toString()
                        latitude = data.getStringExtra("latitude").toString()
                        longitude = data.getStringExtra("longitude").toString()


                    }
                } catch (e: NullPointerException) {
                    println()
                }
            }
        }

    }

    private fun visibilityOfViewsFun() {
        recyclerView.visibility = View.VISIBLE
        seeAllExpertsTv.visibility = View.VISIBLE
        recyclerViewForTopRated.visibility = View.VISIBLE
        seeAllTextView.visibility = View.VISIBLE
        seeAllCategories.visibility = View.VISIBLE
        seeAllExperts.visibility = View.VISIBLE
        topRatedTv.visibility = View.VISIBLE
        topCategoryTv.visibility = View.VISIBLE
        hideRecyclerView.visibility = View.GONE
    }

    private fun searchListeningFun() {
        searchView.addTextChangedListener(object : TextWatcher {


            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length >= 3) {

                    searchApiFun()
                }
                if (s.isEmpty()) {
                    visibilityOfViewsFun()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                recyclerView.visibility = View.GONE
                seeAllExpertsTv.visibility = View.GONE
                recyclerViewForTopRated.visibility = View.GONE
                seeAllTextView.visibility = View.GONE
                seeAllCategories.visibility = View.GONE
                seeAllExperts.visibility = View.GONE
                topRatedTv.visibility = View.GONE
                topCategoryTv.visibility = View.GONE
                hideRecyclerView.visibility = View.VISIBLE

            }

            override fun afterTextChanged(s: Editable) {
                searchList.clear()
            }
        }

        )
    }

    private fun searchViewAdapter(searchList: ArrayList<SearchexpertApiModel.Data>) {
        searchViewAdapter = SearchViewAdapter(requireActivity().applicationContext, searchList)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        hideRecyclerView.layoutManager = layoutManager
        hideRecyclerView.adapter = searchViewAdapter
    }
}

