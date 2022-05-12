package com.example.expertapplication.Activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.expertapplication.Models.CreateGigApiModel
import com.example.expertapplication.R
import com.example.expertapplication.WebService.ParameterService
import com.example.expertapplication.utilis.AppURL
import com.example.expertapplication.utilis.ShareMemory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File


class CreateGigsActivity : AppCompatActivity(), ParameterService.ResponseInterfaces,
    ParameterService.ResponseErrorInterface {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var createGigButton: View
    private lateinit var categoryEditText: View
    private lateinit var titleEditText: EditText
    private lateinit var subCategoryEditText: EditText
    private lateinit var description: EditText
    private lateinit var addImageBtn: TextView
    private lateinit var afterChangeImage: ImageView
    private lateinit var shareMemory: ShareMemory
    private var isCamera: Boolean = true
    private var imageUrl = ""
    private var userId: String = ""
    private var category=""
    private lateinit var spinner: Spinner

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val CAMERA_REQUEST = 1888
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_gigs)
        idViews()
        onClickListener()
        bottomSheetDialog()
        spinner2Fun()
    }

    private fun idViews() {
        createGigButton = findViewById(R.id.creategigview)
        categoryEditText = findViewById(R.id.categoryEdittext)
        titleEditText = findViewById(R.id.gigtitle)
        subCategoryEditText = findViewById(R.id.subcategory)
        description = findViewById(R.id.description)
        addImageBtn = findViewById(R.id.addimageview)
        afterChangeImage = findViewById(R.id.afterchangeimageview)
        shareMemory = ShareMemory.mInstence
        shareMemory = ShareMemory.getmInstence()
        val intent: Intent = intent
        userId = intent.getStringExtra("user_id").toString()
        toolbar = findViewById(R.id.toolbar_actionbar6)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onClickListener() {
        createGigButton.setOnClickListener {
            createGigfun()
        }
        spinner= findViewById(R.id.spinnerforcategory)

    }

    @SuppressLint("InflateParams")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun bottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = this.layoutInflater.inflate(R.layout.bottomsheetforeditimage, null)
        with(bottomSheetDialog) {
            setContentView(bottomSheetView)
        }
        addImageBtn.setOnClickListener {
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
//        if (categoryEditText.text.toString() == "") {
//            Sneaker.with(this) // Activity, Fragment or ViewGroup
//                .setMessage("Please Enter Your Category")
//                .setDuration(2000)
//                .autoHide(true)
//                .sneakError()
//            //etName.requestFocus()
//            return false
//        }
        if (titleEditText.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your GigTitle")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            //etName.requestFocus()
            return false
        }
        if (subCategoryEditText.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your SubCategory")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            //etName.requestFocus()
            return false
        }
        if (description.text.toString() == "") {
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                .setMessage("Please Enter Your Description")
                .setDuration(2000)
                .autoHide(true)
                .sneakError()
            //etName.requestFocus()
            return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun askCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST
            )

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
                    afterChangeImage.setImageBitmap(bitmap)
                    imageUrl = bitmapToByte(bitmap)
//                    updateimagefun()
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
                                afterChangeImage.setImageBitmap(bitmap)
                                imageUrl = bitmapToByte(bitmap)
//                                updateimagefun()

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

    private fun createGigfun() {
        if (setValidation()) {
            val jsonObject = JSONObject()
            val title = titleEditText.text.toString()
//            val category = categoryEditText.toString()
            val subcategory = subCategoryEditText.text.toString()
            val description = description.text.toString()

            jsonObject.put("user_id", shareMemory.userId)
            jsonObject.put("gig_title", title)
            jsonObject.put("category_id", "1")
            jsonObject.put("sub_category", subcategory)
            jsonObject.put("description", description)
            jsonObject.put("image", imageUrl)

            val parameterService = ParameterService(this, this, this)
            parameterService.getData(jsonObject, AppURL.CREATEGIG_URL)
        }
    }

    override fun getResponses(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getBoolean("status")
            if (status == true) {
                val gsonBuilder = GsonBuilder()
                val gson: Gson = gsonBuilder.create()
                val userData = gson.fromJson(`object`.toString(), CreateGigApiModel::class.java)
                finish()
            } else {
                val message = `object`.getString("message")
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun spinner2Fun() {
        val numbers = arrayListOf<String>(
            "Select a category",
            "Graphic Designing",
            "Programming & Tech",
            "Digital Marketing",
            "Website Optimization",
            "Writing & Translation",
            "Business Administration",
            "Lifestyle Industry",
            "Video & Animation",
            "Music & Audio",
            "Business",

            )
        val plantsList: ArrayList<String> = ArrayList()

        // Initializing an ArrayAdapter
        val spinnerArrayAdapter: ArrayAdapter<String?> = object : ArrayAdapter<String?>(
            this, android.R.layout.simple_spinner_dropdown_item, numbers as List<String?>
        ) {
            override fun isEnabled(position: Int): Boolean {
                return if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    false
                } else {
                    true
                }
            }

            @SuppressLint("ResourceAsColor")
            override fun getDropDownView(
                position: Int, convertView: View?,
                parent: ViewGroup?
            ): View {
                val view: View = super.getDropDownView(position, convertView, parent)
                val tv = view as TextView
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(ContextCompat.getColor(context, R.color.colorgry))
                } else {
                    tv.setTextColor(ContextCompat.getColor(context, R.color.black))
                }
                return view
            }
        }
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerArrayAdapter
        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedItemText = parent.getItemAtPosition(position) as String
                    // If user change the default selection
                    // First item is disable and it is used for hint
                    when(selectedItemText){
                        numbers[0]->{
                            (view as TextView).setTextColor(ContextCompat.getColor(this@CreateGigsActivity, R.color.gray))
                        }

                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    override fun getError(o: Any?) {
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

}