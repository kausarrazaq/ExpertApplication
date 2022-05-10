package com.example.expertapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.expertapplication.R
import com.example.expertapplication.WebService.POSTService
import com.example.expertapplication.utilis.AppURL
import com.example.expertapplication.utilis.ShareMemory
import com.irozon.sneaker.Sneaker
import org.json.JSONException
import org.json.JSONObject

class CreateNewPasswordActivity : AppCompatActivity(), POSTService.ResponseInterface {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var setPasswordBtn: View
    private lateinit var passwordEditText: EditText
    private lateinit var shareMemory: ShareMemory
    private var userId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_password)
        idViews()
        onClickListener()
    }

    private fun idViews() {
        setPasswordBtn = findViewById(R.id.setpasswordbtn)
        passwordEditText = findViewById(R.id.password)
        shareMemory = ShareMemory.mInstence
        shareMemory = ShareMemory.getmInstence()
        val intent: Intent = intent
        userId = intent.getStringExtra("user_id").toString()
        toolbar = findViewById(R.id.toolbar_actionbar5)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onClickListener() {
        setPasswordBtn.setOnClickListener {
            changePasswordFun()
        }
    }

    private fun setValidation(): Boolean {
        if (passwordEditText.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Password")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        if (passwordEditText.text.length <= 6) {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Greater then 6 Digit Password")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        return true
    }

    //code for changepasswordApi
    private fun changePasswordFun() {
        if (setValidation()) {
            val jsonObject = JSONObject()


            val password = passwordEditText.text.toString()

            jsonObject.put("user_id", userId)
            jsonObject.put("password", password)


            val postService = POSTService(this, this)
            postService.putData(jsonObject, AppURL.UPDATEPASSWORD_URL)
        }
    }


    override fun getResponse(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getBoolean("status")
            if (status == true) {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finishAffinity()
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

}