package com.example.expertapplication.Models
import com.google.gson.annotations.SerializedName


data class GetExpertsApiModel(
    @SerializedName("data")
    val `data`: List<Data> = listOf(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Boolean = false
) {
    data class Data(
        @SerializedName("category_id")
        val categoryId: String = "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("description")
        val description: String = "",
        @SerializedName("gig_id")
        val gigId: String = "",
        @SerializedName("gig_image")
        val gigImage: String = "",
        @SerializedName("gig_title")
        val gigTitle: String = "",
        @SerializedName("sub_category")
        val subCategory: String = "",
        @SerializedName("user_id")
        val userId: String = "",
        @SerializedName("user_image")
        val userImage: String = "",
        @SerializedName("user_name")
        val userName: String = ""
    )
}