package com.example.expertapplication.Models
import com.google.gson.annotations.SerializedName


data class UserImageApiModel(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Boolean = false
) {
    data class Data(
        @SerializedName("a_code")
        val aCode: String = "",
        @SerializedName("code")
        val code: String = "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("degree")
        val degree: String = "",
        @SerializedName("degree_end_date")
        val degreeEndDate: String = "",
        @SerializedName("degree_image")
        val degreeImage: String = "",
        @SerializedName("degree_start_date")
        val degreeStartDate: String = "",
        @SerializedName("email")
        val email: String = "",
        @SerializedName("email_code")
        val emailCode: String = "",
        @SerializedName("experiance_from")
        val experianceFrom: String = "",
        @SerializedName("experiance_to")
        val experianceTo: String = "",
        @SerializedName("f_code")
        val fCode: String = "",
        @SerializedName("fb_token")
        val fbToken: String = "",
        @SerializedName("g_token")
        val gToken: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("image")
        val image: String = "",
        @SerializedName("institute_name")
        val instituteName: String = "",
        @SerializedName("modified")
        val modified: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("nationality")
        val nationality: String = "",
        @SerializedName("password")
        val password: String = "",
        @SerializedName("phone_number")
        val phoneNumber: String = "",
        @SerializedName("shop_registered")
        val shopRegistered: String = "",
        @SerializedName("status")
        val status: String = "",
        @SerializedName("user_type")
        val userType: String = ""
    )
}