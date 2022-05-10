package com.example.expertapplication.Models
import com.google.gson.annotations.SerializedName


data class GigCategorySearchFilterModel(
    @SerializedName("data")
    val `data`: List<Data> = listOf(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Boolean = false
) {
    data class Data(
        @SerializedName("category")
        val category: String = ""
    )
}