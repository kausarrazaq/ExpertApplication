package com.example.expertapplication.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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
import org.json.JSONObject

const val RC_SIGN_IN = 123

class StartActivity : AppCompatActivity(), ParameterService.ResponseInterfaces,
    ParameterService.ResponseErrorInterface {
    private lateinit var signInBtn: View
    private lateinit var signUpTextView: TextView
    private lateinit var googleView: View
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var shareMemory: ShareMemory

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        //code for login with google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //you can also use R.string.default_web_client_id
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        idViews()
        onClickListener()
        setStatusBarTransparent()

    }

    private fun idViews() {
        signInBtn = findViewById(R.id.signInBtn)
        signUpTextView = findViewById(R.id.signuptv)
        googleView = findViewById(R.id.loginwithgoogle)
        shareMemory = ShareMemory.mInstence

        if (shareMemory.userId.isNotEmpty()) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun onClickListener() {
        signUpTextView.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }
        signInBtn.setOnClickListener {
            val i = Intent(this, SignInActivity::class.java)
            startActivity(i)
        }
        googleView.setOnClickListener {
            signIn()
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val decorView: View = window.getDecorView()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            window.setStatusBarColor(Color.TRANSPARENT)
        }
    }

    //code for onactivity result for login
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun signIn() {
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

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) = try {
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

    override fun getError(o: Any?) {
    }

    override fun getResponses(o: Any?) {
    }
}


