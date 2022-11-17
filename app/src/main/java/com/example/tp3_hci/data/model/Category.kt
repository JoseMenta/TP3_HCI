package com.example.tp3_hci.data.model

import com.google.gson.annotations.SerializedName

data class Category(
    val id     : Int,
    val name   : String,
    var detail : String? = null
)
