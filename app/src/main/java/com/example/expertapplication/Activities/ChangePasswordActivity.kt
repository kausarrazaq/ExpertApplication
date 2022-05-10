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

class ChangePasswordActivity : AppCompatActivity(), POSTService.ResponseInterface {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var changePasswordBtn: View
    private lateinit var oldPassword: EditText
    private lateinit var newPassword: EditText
    private lateinit var shareMemory: ShareMemory
    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        idViews()
        onClickListener()
    }

    private fun idViews() {
        toolbar = findViewById(R.id.toolbar_actionbar6)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        changePasswordBtn = findViewById(R.id.changepasswordbtn)
        oldPassword = findViewById(R.id.oldpassword)
        newPassword = findViewById(R.id.newpassword)
        shareMemory = ShareMemory.mInstence
        shareMemory = ShareMemory.getmInstence()
        val intent: Intent = intent
        userId = intent.getStringExtra("user_id").toString()
    }

    private fun onClickListener() {
        changePasswordBtn.setOnClickListener {
            ResetPasswordFun()
        }
    }

    private fun setValidation(): Boolean {
        if (oldPassword.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Old Password")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        if (newPassword.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your New Password")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        if (oldPassword.text.length <= 6) {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Greater then 6 Digit Password")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        return true

    }

    private fun ResetPasswordFun() {
        if (setValidation()) {
            val jsonObject = JSONObject()


            val password = oldPassword.text.toString()
            val newpassword = newPassword.text.toString()

            jsonObject.put("user_id", shareMemory.userId)
            jsonObject.put("old_password", password)
            jsonObject.put("new_password", newpassword)

            val postService = POSTService(this, this)
            postService.putData(jsonObject, AppURL.RESETPASSWORD_URL)
        }
    }


    override fun getResponse(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getBoolean("status")
            if (status == true) {
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
}