package com.dicoding.picodiploma.kaidahapp.dataregulation

import com.google.gson.annotations.SerializedName

data class SpecialSerialized(

    @SerializedName("current_page")
    val page: Int,


    @SerializedName("data")
    val data: ArrayList<DataSerialized>,

    @SerializedName("next_page_url")
    val nextPage: String
)