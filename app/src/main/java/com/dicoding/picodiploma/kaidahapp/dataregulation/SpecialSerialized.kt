package com.dicoding.picodiploma.kaidahapp.dataregulation

import com.google.gson.annotations.SerializedName

data class SpecialSerialized(
    @SerializedName("data")
    val data: ArrayList<DataSerialized>
)