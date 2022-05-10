package com.example.expertapplication.Activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.expertapplication.R
import com.example.expertapplication.utilis.ShareMemory

class UserProfileActivity : AppCompatActivity() {
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
