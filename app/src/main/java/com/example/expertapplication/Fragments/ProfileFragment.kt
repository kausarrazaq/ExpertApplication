package com.example.expertapplication.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.expertapplication.Activities.ChangePasswordActivity
import com.example.expertapplication.Activities.EditProfileActivity
import com.example.expertapplication.Activities.SignInActivity
import com.example.expertapplication.R
import com.example.expertapplication.utilis.ShareMemory

class ProfileFragment : Fragment() {
    private lateinit var changePasswordView: View
    private lateinit var editProfile: View
    private lateinit var logOutBtn: View
    private lateinit var userNameTv: TextView
    private lateinit var shareMemory: ShareMemory
    private lateinit var updateImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initialisation(view)

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initialisation(view: View) {
        changePasswordView = view.findViewById(R.id.changepasswordview)
        editProfile = view.findViewById(R.id.editprofile)
        logOutBtn = view.findViewById(R.id.logoutbtn)
        userNameTv = view.findViewById(R.id.changenameTv)
        shareMemory = ShareMemory.mInstence
        shareMemory = ShareMemory.getmInstence()
        updateImage = view.findViewById(R.id.updateimage)
        logOutBtn.setOnClickListener {
            activity?.let {
                val intent: Intent = Intent(it, SignInActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
//                shareMemory.deleteAllSharedPrefs()
            }
        }
        editProfile.setOnClickListener {
            activity?.let {
                val intent = Intent(it, EditProfileActivity::class.java)
                it.startActivity(intent)
            }
        }
        changePasswordView.setOnClickListener {
            activity?.let {
                val intent = Intent(it, ChangePasswordActivity::class.java)
                it.startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        userNameTv.text = shareMemory.userName
        Glide.with(this)
            .load(shareMemory.profileImageUrl)
            .placeholder(R.drawable.profileicon)
            .into(updateImage)
    }

}