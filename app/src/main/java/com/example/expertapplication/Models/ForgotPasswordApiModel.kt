package com.example.expertapplication.Models
import com.google.gson.annotations.SerializedName


data class ForgotPasswordApiModel(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Boolean = false
) {
    class Data
}