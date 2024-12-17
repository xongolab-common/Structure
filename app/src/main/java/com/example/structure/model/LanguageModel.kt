package com.example.structure.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LanguageModel(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("_id")
    val id: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("AR")
    val aR: String = "",
    @SerializedName("EN")
    val eN: String = "",
    @SerializedName("label_code")
    val labelCode: String = "",
    @SerializedName("label_id")
    val labelId: String = "",
    @SerializedName("text")
    val text: String = "",
)