package com.example.expertapplication.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.expertapplication.Models.SignUpApiModel
import com.example.expertapplication.R
import com.example.expertapplication.WebService.ParameterService
import com.example.expertapplication.utilis.AppURL
import com.example.expertapplication.utilis.ShareMemory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import org.json.JSONException
import org.json.JSONObject

const val RC_SIGN_IN1 = 123

class SignUpActivity : AppCompatActivity(), ParameterService.ResponseInterfaces,
    ParameterService.ResponseErrorInterface {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var signUpBtn: View
    private lateinit var signInTv: TextView
    private lateinit var loginEmailEditText: EditText
    private lateinit var loginPasswordEditText: EditText
    private lateinit var loginUserNameEditText: EditText
    private lateinit var registerGoogleView: View
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var shareMemory: ShareMemory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //you can also use R.string.default_web_client_id
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        idViews()
        onClickListener()
    }

    private fun idViews() {
        signInTv = findViewById(R.id.signintextview)
        signUpBtn = findViewById(R.id.signupbtn)
        loginEmailEditText = findViewById(R.id.edittextforemail)
        loginPasswordEditText = findViewById(R.id.edittextforpassword)
        loginUserNameEditText = findViewById(R.id.edittextforusername)
        registerGoogleView = findViewById(R.id.signupview)
        shareMemory = ShareMemory.mInstence
        toolbar = findViewById(R.id.toolbar_actionbar2)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onClickListener() {
        signUpBtn.setOnClickListener {
            signUpApifun()
        }

        signInTv.setOnClickListener {
            val i = Intent(this, SignInActivity::class.java)
            startActivity(i)
        }
        registerGoogleView.setOnClickListener {
            Signupwithgoogle()
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
        if (loginUserNameEditText.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Name")
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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun signUpApifun() {
        if (setValidation()) {


            val email = loginEmailEditText.text.toString()
            val name = loginUserNameEditText.text.toString()
            val password = loginPasswordEditText.text.toString()

            val jsonObject = JSONObject()
            jsonObject.put("name", name)
            jsonObject.put("email", email)
            jsonObject.put("password", password)


            val parameterService = ParameterService(this, this, this)
            parameterService.getData(jsonObject, AppURL.SIGNUP_URL)


        }
    }

    override fun getResponses(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getBoolean("status")
            if (status) {
                val gsonBuilder = GsonBuilder()
                val gson: Gson = gsonBuilder.create()
                val userData = gson.fromJson(`object`.toString(), SignUpApiModel::class.java)
                shareMemory.userId = userData.data.id
                shareMemory.userName = userData.data.name
                val intent = Intent(this, HomeActivity::class.java)
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

    private fun Signupwithgoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val task =
                    GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
        }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )

            val name: String? = account.displayName
            val email: String? = account.email
            val socialToken: String? = account.id

            val jsonObject = JSONObject()
            jsonObject.put("email", email)
            jsonObject.put("name", name)
            jsonObject.put("user_type", "user")
            jsonObject.put("social_key", "google")
            jsonObject.put("social_token", socialToken)


            if (socialToken!!.isEmpty()) {
                val parameterService = ParameterService(this, this, this)
                parameterService.getData(jsonObject, AppURL.SIGNINWITHGOOGLE_URL)
            } else {
                val parameterService = ParameterService(this, this, this)
                parameterService.getData(jsonObject, AppURL.SIGNINWITHGOOGLE_URL)

            }
        } catch (e: ApiException) {
            // Sign in was unsuccessful
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun getError(o: Any?) {
    }
}

