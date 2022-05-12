package com.example.expertapplication.Models
import com.google.gson.annotations.SerializedName


data class RatesApiModel(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Boolean = false
) {
    data class Data(
        @SerializedName("comment")
        val comment: String = "",
        @SerializedName("created")
        val created: String = "",
        @SerializedName("rate")
        val rate: String = "",
        @SerializedName("rate_id")
        val rateId: String = "",
        @SerializedName("user_id")
        val userId: String = ""
    )
}