package com.example.expertapplication.Activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.expertapplication.Models.UserImageApiModel
import com.example.expertapplication.Models.UserProfileApiModel
import com.example.expertapplication.R
import com.example.expertapplication.WebService.POSTService
import com.example.expertapplication.utilis.AppURL
import com.example.expertapplication.utilis.ShareMemory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.irozon.sneaker.Sneaker
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File

class EditProfileActivity : AppCompatActivity(), POSTService.ResponseInterface {
    private lateinit var savebtn: View
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var costEditText: EditText
    private lateinit var costTraveleditText: EditText
    private lateinit var locationEditText: View
    private lateinit var aboutEditText: EditText
    private lateinit var beforeEditImage: ImageView
    private lateinit var afterEditImage: ImageView
    private var isCamera: Boolean = true
    private var imageUrl = ""
    private lateinit var userNameTv: TextView
    private lateinit var shareMemory: ShareMemory

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val CAMERA_REQUEST = 1888

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        idViews()
        onClickListener()
        bottomSheetDialog()
    }

    private fun idViews() {
        savebtn = findViewById(R.id.saveview)
        costEditText = findViewById(R.id.costedittext)
        costTraveleditText = findViewById(R.id.costtravel)
        beforeEditImage = findViewById(R.id.beforeeditpic)
        afterEditImage = findViewById(R.id.aftereditpic)
        locationEditText = findViewById(R.id.location)
        aboutEditText = findViewById(R.id.aboutedittext)
        userNameTv = findViewById(R.id.namechangeTV)
        shareMemory = ShareMemory.mInstence
        shareMemory = ShareMemory.getmInstence()
        toolbar = findViewById(R.id.toolbar_actionbar10)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (shareMemory.profileImageUrl.isNotEmpty()) {
            Glide.with(this)
                .load(shareMemory.profileImageUrl)
                .placeholder(R.drawable.profileicon)
                .into(afterEditImage)
        }
    }

    private fun onClickListener() {
        savebtn.setOnClickListener {
//            if (setValidation()) {
//                finish()
//            } else {
////                Toast.makeText(this, "Please Fill All fields", Toast.LENGTH_SHORT).show()
//            }
            userProfileApi()
        }
    }

    @SuppressLint("InflateParams")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun bottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = this.layoutInflater.inflate(R.layout.bottomsheetforeditimage, null)
        with(bottomSheetDialog) {
            setContentView(bottomSheetView)
        }
        beforeEditImage.setOnClickListener {
            showDialogNotificationAction(bottomSheetDialog)
        }
        bottomSheetView.findViewById<LinearLayout>(R.id.camBtn).setOnClickListener {
            askCameraPermission()
            bottomSheetDialog.dismiss()
        }
        bottomSheetView.findViewById<LinearLayout>(R.id.galBtn).setOnClickListener {
            askGalleryPermission()
            bottomSheetDialog.dismiss()
        }
        bottomSheetView.findViewById<LinearLayout>(R.id.cancelBtn).setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }


    private fun setValidation(): Boolean {
        // Check for a valid email address.
        if (costEditText.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Cost")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            //etName.requestFocus()
            return false
        }
        if (costTraveleditText.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Cost/Travel")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            //etName.requestFocus()
            return false
        }
        if (aboutEditText.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter About Field")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            //etName.requestFocus()
            return false
        }
//        if (locationEditText.toString() == "") {
//            Sneaker.with(this) // Activity, Fragment or ViewGroup
//                .setMessage("Please Enter Your Location")
//                .setDuration(2000)
//                .autoHide(true)
//                .sneakError()
//            //etName.requestFocus()
//            return false
//        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun askCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST)

        } else {
            openCamera()
        }
    }

    private fun openCamera() {
        isCamera = true
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(intent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun askGalleryPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) ==
            PackageManager.PERMISSION_DENIED
        ) {
            //permission denied
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            //show popup to request runtime permission
            requestPermissions(permissions, IMAGE_PICK_CODE)
        } else {
            openGalleryForImage()
        }
    }


    //foropengallery
    private fun openGalleryForImage() {
        isCamera = false
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private fun bitmapToByte(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<String>,
        @NonNull grantResults: IntArray,
    ) {   /*grantResults.isNotEmpty()*/
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            IMAGE_PICK_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup granted
                    openGalleryForImage()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Gallery Permission denied", Toast.LENGTH_SHORT).show()
                    // ShareResource().showAlertMessage(this, "Gallery Permission denied", false)
                }
            }
            CAMERA_REQUEST -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(this, "Camera Permission denied", Toast.LENGTH_SHORT).show()
                    // ShareResource().showAlertMessage(this, "Camera Permission denied", false)
                }
            }
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // There are no request codes
                if (isCamera) {
                    val data = result.data
                    val bitmap: Bitmap = data?.extras?.get("data") as Bitmap
                    afterEditImage.setImageBitmap(bitmap)
                    imageUrl = bitmapToByte(bitmap)
                    updateimagefun()
                } else {
                    val data: Intent? = result.data
                    val uri: Uri? = data?.data
                    val inputStream = this.contentResolver.openInputStream(uri!!)
                    val cursor = this.contentResolver.query(uri, null, null, null, null)
                    cursor?.use { c ->
                        val nameIndex = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (c.moveToFirst()) {
                            val name = c.getString(nameIndex)
                            inputStream?.let { inputStream ->
                                // create same file with same name
                                val file = File(this.cacheDir, name)
                                val os = file.outputStream()
                                os.use {
                                    inputStream.copyTo(it)
                                }
                                val options: BitmapFactory.Options = BitmapFactory.Options()
                                options.inSampleSize = 3
                                val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
                                afterEditImage.setImageBitmap(bitmap)
                                imageUrl = bitmapToByte(bitmap)
                                updateimagefun()

                            }
                        }
                    }
                }
            }
        }

    private fun showDialogNotificationAction(bottomSheetDialog: BottomSheetDialog) {
        bottomSheetDialog.show()
        val bottomSheetDialogFrameLayout =
            bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheetDialogFrameLayout?.background = null
    }

    private fun updateimagefun() {
        val jsonObject = JSONObject()


        jsonObject.put("user_id", shareMemory.userId)
        jsonObject.put("image", imageUrl)


        val postService = POSTService(this, this)
        postService.putData(jsonObject, AppURL.USERIMAGE_URL)
    }


    private fun userProfileApi() {
        if (setValidation()) {
            val jsonObject = JSONObject()


            val lat = locationEditText.toString()
            val cost= costEditText.text.toString()
            val travel= costTraveleditText.text.toString()
            val about= aboutEditText.text.toString()

            jsonObject.put("user_id", shareMemory.userId)
            jsonObject.put("lat",lat )
            jsonObject.put("longi",lat)
            jsonObject.put("cost",cost)
            jsonObject.put("travel",travel)
            jsonObject.put("about",about)


            val postService = POSTService(this, this)
            postService.putData(jsonObject, AppURL.USERPROFILE_URL)
        }
    }
    override fun getResponse(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getString("status")
            if (status == "OK") {
                val gsonBuilder = GsonBuilder()
                val gson: Gson = gsonBuilder.create()
                val userData = gson.fromJson(`object`.toString(), UserImageApiModel::class.java)
                shareMemory.profileImageUrl = userData.data.image


            }
            val status1 = `object`.getBoolean("status")
            if (status1 == true) {

                val gsonBuilder = GsonBuilder()
                val gson: Gson = gsonBuilder.create()
                val userData = gson.fromJson(`object`.toString(), UserProfileApiModel::class.java)
                // getQuestionAdapterFun(userData.data)
                shareMemory.userName = userData.data.name
                shareMemory.profileImageUrl = userData.data.image
                shareMemory.userEmail = userData.data.email
                finish()

            } else {
            val message = `object`.getString("message")
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage(message)
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
        }
    }catch (e: JSONException) {
        e.printStackTrace()
    }catch (e: JsonSyntaxException){
        e.printStackTrace()
    }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        userNameTv.text = shareMemory.userName
    }
}