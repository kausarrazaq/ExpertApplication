package com.example.expertapplication.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expertapplication.Models.RatesApiModel
import com.example.expertapplication.Models.SignInApiModel
import com.example.expertapplication.R
import com.example.expertapplication.WebService.ParameterService
import com.example.expertapplication.utilis.AppURL
import com.example.expertapplication.utilis.ShareMemory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject


class RatesActivity : AppCompatActivity(), ParameterService.ResponseInterfaces,
    ParameterService.ResponseErrorInterface {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var submitBtn: View
    private lateinit var ratesEditText: EditText
    private lateinit var commentEditText: EditText
    private var userId: String = ""
    private lateinit var shareMemory: ShareMemory

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rates)
        idViews()
        onClickListener()
    }

    private fun idViews() {
        submitBtn = findViewById(R.id.submitbutton)
        ratesEditText= findViewById(R.id.ratesedittxt)
        commentEditText= findViewById(R.id.commentedittext)
        shareMemory = ShareMemory.mInstence
        shareMemory = ShareMemory.getmInstence()
        val intent: Intent = intent
        userId = intent.getStringExtra("user_id").toString()
        toolbar = findViewById(R.id.toolbar_actionbar12)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onClickListener() {
        submitBtn.setOnClickListener {
            ratesApiFun()
        }
    }
    private fun ratesApiFun() {
            val jsonObject = JSONObject()
            val rates = ratesEditText.text.toString()
            val comment = commentEditText.text.toString()

            jsonObject.put("rate", rates)
            jsonObject.put("comment", comment)
            jsonObject.put("user_id", shareMemory.userId)

            val parameterService = ParameterService(this, this, this)
            parameterService.getData(jsonObject, AppURL.RATING_URL)
        }


    override fun getResponses(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getBoolean("status")
            if (status == true) {
                val gsonBuilder = GsonBuilder()
                val gson: Gson = gsonBuilder.create()
                val userData = gson.fromJson(`object`.toString(), RatesApiModel::class.java)
                finish()
            } else {
                val message = `object`.getString("message")
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)

    }

    override fun getError(o: Any?) {
    }
}
