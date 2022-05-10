package com.example.expertapplication.Models
import com.google.gson.annotations.SerializedName


data class CreateGigApiModel(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Boolean = false
) {
    data class Data(
        @SerializedName("category")
        val category: String = "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("description")
        val description: String = "",
        @SerializedName("gig_id")
        val gigId: String = "",
        @SerializedName("gig_title")
        val gigTitle: String = "",
        @SerializedName("image")
        val image: String = "",
        @SerializedName("sub_category")
        val subCategory: String = "",
        @SerializedName("user_id")
        val userId: String = ""
    )
}