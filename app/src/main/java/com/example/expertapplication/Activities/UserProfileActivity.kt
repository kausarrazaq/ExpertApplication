package com.example.expertapplication.Activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.bumptech.glide.Glide
import com.example.expertapplication.Models.GetExpertsApiModel
import com.example.expertapplication.R
import com.example.expertapplication.WebService.POSTService
import com.example.expertapplication.utilis.AppURL
import com.example.expertapplication.utilis.ShareMemory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject

class UserProfileActivity : AppCompatActivity(),POSTService.ResponseInterface  {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var messageView: View
    private lateinit var nameTextView: TextView
    private lateinit var shareMemory: ShareMemory
    private lateinit var imageView: ImageView
    private lateinit var titleTv: TextView
    private lateinit var categoryTv: TextView
    private lateinit var subCategoryTv: TextView
    private lateinit var description: TextView
    private lateinit var profileImageView: ImageView
    private var user_id = ""
    private var gig_id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        idViews()
        onClickListener()
    }

    @SuppressLint("CutPasteId")
    private fun idViews() {
        messageView = findViewById(R.id.messageview)
        nameTextView = findViewById(R.id.nametextview)
        imageView = findViewById(R.id.creategigimage)
        titleTv = findViewById(R.id.titletv)
        categoryTv = findViewById(R.id.categorytv)
        subCategoryTv = findViewById(R.id.subcategorytv)
        description = findViewById(R.id.descriptiontv)
        profileImageView = findViewById(R.id.profile)
        shareMemory = ShareMemory.mInstence
        shareMemory = ShareMemory.getmInstence()
        toolbar = findViewById(R.id.toolbar_actionbar6)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onClickListener() {
        messageView.setOnClickListener {
            val i = Intent(this, MessageActivity::class.java)
            startActivity(i)
        }
    }
    private fun getExpertApiFun(){
        val postService = POSTService(this, this)
        postService.getDataWithoutParams(
            AppURL.GETEXPERTS_URL ,
            Request.Method.GET,

            )
    }
    override fun getResponse(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getString("status")
            if (status == "OK") {
                val gsonBuilder = GsonBuilder()
                val gson: Gson = gsonBuilder.create()
                val userData =
                    gson.fromJson(`object`.toString(), GetExpertsApiModel::class.java)
//                  name.text=userData.data.name
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        nameTextView.text = shareMemory.userName
        Glide.with(this)
            .load(shareMemory.profileImageUrl)
            .placeholder(R.drawable.profileicon)
            .into(profileImageView)
    }
}
