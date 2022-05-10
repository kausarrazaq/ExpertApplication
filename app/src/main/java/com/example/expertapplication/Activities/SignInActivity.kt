package com.example.expertapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.expertapplication.Models.SignInApiModel
import com.example.expertapplication.R
import com.example.expertapplication.WebService.ParameterService
import com.example.expertapplication.utilis.AppURL
import com.example.expertapplication.utilis.ShareMemory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import org.json.JSONException
import org.json.JSONObject

class SignInActivity : AppCompatActivity(), ParameterService.ResponseInterfaces,
    ParameterService.ResponseErrorInterface {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var continueBtn: View
    private lateinit var forgotPasswordTV: TextView
    private lateinit var loginEmailEditText: EditText
    private lateinit var loginPasswordEditText: EditText
    private lateinit var shareMemory: ShareMemory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        idViews()
        onClickListener()
    }

    private fun idViews() {
        continueBtn = findViewById(R.id.contiuebtn)
        loginEmailEditText = findViewById(R.id.login_email_edit_text)
        loginPasswordEditText = findViewById(R.id.edittextforpassword)
        forgotPasswordTV = findViewById(R.id.forgotpasswordtv)
        shareMemory = ShareMemory.mInstence
        shareMemory = ShareMemory.getmInstence()
        toolbar = findViewById(R.id.toolbar_actionbar1)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onClickListener() {
        forgotPasswordTV.setOnClickListener {
            val i = Intent(this, ForgotpasswordmainActivity::class.java)
            startActivity(i)
        }
        continueBtn.setOnClickListener {
            loginApiFun()
        }
    }

    private fun setValidation(): Boolean {
        // Check for a valid email address.
        if (loginEmailEditText.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Email")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            //etName.requestFocus()
            return false
        }

        if (loginPasswordEditText.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Password")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        if (loginPasswordEditText.text.length <= 6) {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Greater then 6 Digit Password")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        return true
    }

    private fun loginApiFun() {
        if (setValidation()) {
            val jsonObject = JSONObject()
            val email = loginEmailEditText.text.toString()
            val password = loginPasswordEditText.text.toString()

            jsonObject.put("email", email)
            jsonObject.put("password", password)


            val parameterService = ParameterService(this, this, this)
            parameterService.getData(jsonObject, AppURL.SIGNIN_URL)
        }
    }


    override fun getResponses(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getBoolean("status")
            if (status == true) {
                val gsonBuilder = GsonBuilder()
                val gson: Gson = gsonBuilder.create()
                val userData = gson.fromJson(`object`.toString(), SignInApiModel::class.java)
                shareMemory.userId = userData.data.id
                shareMemory.userName = userData.data.name
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
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
        Toast.makeText(this, o.toString(), Toast.LENGTH_SHORT).show()
    }
}

