package com.example.expertapplication.Models
import com.google.gson.annotations.SerializedName


data class UserProfileApiModel(
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
        @SerializedName("about")
        val about: String = "",
        @SerializedName("code")
        val code: String = "",
        @SerializedName("cost")
        val cost: String = "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("email")
        val email: String = "",
        @SerializedName("email_code")
        val emailCode: String = "",
        @SerializedName("f_code")
        val fCode: String = "",
        @SerializedName("fb_token")
        val fbToken: String = "",
        @SerializedName("fireBase_id")
        val fireBaseId: String = "",
        @SerializedName("g_token")
        val gToken: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("image")
        val image: String = "",
        @SerializedName("lat")
        val lat: String = "",
        @SerializedName("longi")
        val longi: String = "",
        @SerializedName("modified")
        val modified: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("password")
        val password: String = "",
        @SerializedName("phone_number")
        val phoneNumber: String = "",
        @SerializedName("status")
        val status: String = "",
        @SerializedName("travel")
        val travel: String = "",
        @SerializedName("user_type")
        val userType: String = ""
    )
}