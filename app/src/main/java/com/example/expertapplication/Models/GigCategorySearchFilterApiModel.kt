package com.example.expertapplication.Models
import com.google.gson.annotations.SerializedName


data class GigCategorySearchFilterApiModel(
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
        @SerializedName("category_image")
        val categoryImage: String = "",
        @SerializedName("category_name")
        val categoryName: String = ""
    )
}