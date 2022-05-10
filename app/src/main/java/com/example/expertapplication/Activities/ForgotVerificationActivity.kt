package com.example.expertapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.expertapplication.Models.VerifyCodeApiModel
import com.example.expertapplication.R
import com.example.expertapplication.WebService.ParameterService
import com.example.expertapplication.utilis.AppURL
import com.example.expertapplication.utilis.ShareMemory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import org.json.JSONException
import org.json.JSONObject

class ForgotVerificationActivity : AppCompatActivity(), ParameterService.ResponseInterfaces,
    ParameterService.ResponseErrorInterface {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var submitBtn: View
    private lateinit var shareMemory: ShareMemory
    private lateinit var code1: EditText
    private lateinit var code2: EditText
    private lateinit var code3: EditText
    private lateinit var code4: EditText
    private lateinit var changeEmail: TextView
    private var email: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_verification)
        idViews()
        onClickListener()
        codeSendfun()
        emailchangetext()
    }

    private fun idViews() {
        submitBtn = findViewById(R.id.submitbtn)
        toolbar = findViewById(R.id.toolbar_actionbar4)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        code1 = findViewById(R.id.code1)
        code2 = findViewById(R.id.code2)
        code3 = findViewById(R.id.code3)
        code4 = findViewById(R.id.code4)
        shareMemory = ShareMemory.mInstence
        shareMemory = ShareMemory.getmInstence()
        changeEmail = findViewById(R.id.changeemail)
        val intent: Intent = intent
        email = intent.getStringExtra("email").toString()
    }

    private fun onClickListener() {
        submitBtn.setOnClickListener {
            passwordverifyApi()
        }
    }

    //code for passwordverifyApi
    private fun passwordverifyApi() {
        if (setValidation()) {
            val jsonObject = JSONObject()


            jsonObject.put("email", email)
            jsonObject.put(
                "code",
                "${code1.text.toString()}${code2.text.toString()}${code3.text.toString()}${code4.text.toString()}"
            )

            val parameterService = ParameterService(this, this, this)
            parameterService.getData(jsonObject, AppURL.VERIFYCODE_URL)
        }
    }


    override fun getResponses(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getBoolean("status")
            if (status) {
                val gsonBuilder = GsonBuilder()
                val gson: Gson = gsonBuilder.create()
                val userData =
                    gson.fromJson(`object`.toString(), VerifyCodeApiModel::class.java)
                val userId = userData.data.id
                val intent = Intent(this, CreateNewPasswordActivity::class.java)
                intent.putExtra("user_id", userId)
                startActivity(intent)

            } else {
                val message = `object`.getString("message")
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun setValidation(): Boolean {
        if (code1.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Enter Your code")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        if (code2.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Enter Your code")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        if (code3.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Enter Your code")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        }
        if (code4.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Enter Your code")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            return false
        } else {
            return true
        }
    }

    private fun emailchangetext() {
        //code for one text have two colors
        val text =
            "<font color=##6F6F6F>Please enter the 4-Digit code send to you at</font> <font color=#000000>$email</font>"
        changeEmail.setText(Html.fromHtml(text))
    }

    private fun codeSendfun() {
        //for verify code
        code1.addTextChangedListener(GenericTextWatcher(code1, code2))
        code2.addTextChangedListener(GenericTextWatcher(code2, code3))
        code3.addTextChangedListener(GenericTextWatcher(code3, code4))
        code4.addTextChangedListener(GenericTextWatcher(code4, null))

//GenericKeyEvent here works for deleting the element and to switch back to previous EditText
//first parameter is the current EditText and second parameter is previous EditText
        code1.setOnKeyListener(GenericKeyEvent(code1, null))
        code2.setOnKeyListener(GenericKeyEvent(code2, code1))
        code3.setOnKeyListener(GenericKeyEvent(code3, code2))
        code4.setOnKeyListener(GenericKeyEvent(code4, code3))

    }

    //for verify code
    class GenericKeyEvent internal constructor(
        private val currentView: EditText,
        private val previousView: EditText?
    ) : View.OnKeyListener {
        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.code1 && currentView.text.isEmpty()) {
                //If current is empty then previous EditText's number will also be deleted
                previousView!!.text = null
                previousView.requestFocus()
                return true
            }
            return false
        }

    }

    class GenericTextWatcher internal constructor(
        private val currentView: View,
        private val nextView: View?
    ) :
        TextWatcher {
        override fun afterTextChanged(editable: Editable) { // TODO Auto-generated method stub
            val text = editable.toString()
            when (currentView.id) {
                R.id.code1 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.code2 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.code3 -> if (text.length == 1) nextView!!.requestFocus()
                //You can use EditText4 same as above to hide the keyboard
            }
        }

        override fun beforeTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
        ) { // TODO Auto-generated method stub
        }

        override fun onTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
        ) { // TODO Auto-generated method stub
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun getError(o: Any?) {
    }
}