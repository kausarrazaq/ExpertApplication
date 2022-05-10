package com.example.expertapplication.Activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.example.expertapplication.Adapter.FilterAdapter
import com.example.expertapplication.Models.GigCategorySearchFilterModel
import com.example.expertapplication.Models.FilterModel
import com.example.expertapplication.R
import com.example.expertapplication.WebService.POSTService
import com.example.expertapplication.utilis.AppURL
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class FilterActivity : AppCompatActivity(), POSTService.ResponseInterface,
    FilterAdapter.ItemClickListener {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var applyBtn: View
    private lateinit var filterAdapter: FilterAdapter
    private val filterList = ArrayList<FilterModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var locationTextView: TextView
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var categoryName = ""

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val AUTOCOMPLETE_REQUEST_CODE = 200
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        idViews()
        onClickListener()
        initializePlaceApi()
        categoryApiCallingFun()
    }

    private fun idViews() {
        applyBtn = findViewById(R.id.applybtn)
        recyclerView = findViewById(R.id.filterrecycler)
        locationTextView = findViewById(R.id.location_text)
        toolbar = findViewById(R.id.toolbar_actionbar8)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onClickListener() {
        applyBtn.setOnClickListener {
            val intent = Intent()

            intent.putExtra("categoryName", categoryName)
            intent.putExtra("latitude", latitude)
            intent.putExtra("longitude", longitude)
            intent.putExtra("isChecked", false)
            setResult(RESULT_OK, intent)
            finish()
        }
    }


    private fun initializePlaceApi() {


        if (!Places.isInitialized())
            Places.initialize(applicationContext, "AIzaSyCwu7xCTFEh5zGtNh9phy_Rld65_4If8")

        locationTextView.setOnClickListener {
            onGooglePlacesSearchCalled()
        }

    }

    private fun onGooglePlacesSearchCalled() {

        // Set the fields to specify which types of place data to return.
        val fields = listOf(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG
        )
        // Start the autocomplete intent.
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.FULLSCREEN, fields
        )
            .build(Objects.requireNonNull(this))
        startActivityForResult(intent, FilterActivity.AUTOCOMPLETE_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FilterActivity.AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data!!)
                val address = place.address
                locationTextView.text = address
                val currentString = place.latLng!!.toString() + ""
                val separated = currentString.split(",").toTypedArray()
                val sperateLat = separated[0]
                val sperateLng = separated[1]
                latitude = sperateLat.split("(")[1].toDouble()
                longitude = sperateLng.split(")")[0].toDouble()


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
//                val status = Autocomplete.getStatusFromIntent(data)
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)

    }

    private fun categoryApiCallingFun() {
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
                    gson.fromJson(`object`.toString(), GigCategorySearchFilterModel::class.java)
                categoryAdapter(userData.data)

            } else {
                val message = `object`.getString("message")
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun categoryAdapter(data: List<GigCategorySearchFilterModel.Data>) {
        filterAdapter = FilterAdapter(this, this, data)
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = filterAdapter
    }

    override fun onItemClick(category: String) {
        categoryName = category
    }

}

