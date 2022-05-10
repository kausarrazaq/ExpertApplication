package com.example.expertapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.expertapplication.R
import com.example.expertapplication.WebService.ParameterService
import com.example.expertapplication.utilis.AppURL
import com.example.expertapplication.utilis.ShareMemory
import com.irozon.sneaker.Sneaker
import org.json.JSONException
import org.json.JSONObject

class ForgotpasswordmainActivity : AppCompatActivity(), ParameterService.ResponseInterfaces,
    ParameterService.ResponseErrorInterface {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var forgotPasswordBtn: View
    private lateinit var emailEditText: EditText
    private lateinit var shareMemory: ShareMemory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpasswordmain)
        idViews()
        onClickListener()
    }

    private fun idViews() {
        forgotPasswordBtn = findViewById(R.id.forgotpasswordbtn)
        emailEditText = findViewById(R.id.emailedittext)
        toolbar = findViewById(R.id.toolbar_actionbar3)
        shareMemory = ShareMemory.mInstence
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onClickListener() {
        forgotPasswordBtn.setOnClickListener {
            forgotpasswordApi()
        }
    }

    private fun setValidation(): Boolean {
        // Check for a valid email address.
        if (emailEditText.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Email")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            //etName.requestFocus()
            return false
        }
        return true
    }

    private fun forgotpasswordApi() {
        if (setValidation()) {
            val jsonObject = JSONObject()
            val email = emailEditText.text.toString()

            jsonObject.put("email", email)

            val parameterService = ParameterService(this, this, this)
            parameterService.getData(jsonObject, AppURL.FORGOTPASSWORD_URL)
        }
    }

    override fun getResponses(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getBoolean("status")
            if (status == true) {

                val email = emailEditText.text.toString()
                val intent1 = Intent(this, ForgotVerificationActivity::class.java)
                intent1.putExtra("email", email)
                startActivity(intent1)

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