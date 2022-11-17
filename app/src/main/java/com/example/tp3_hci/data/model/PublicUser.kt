package com.example.tp3_hci.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class PublicUser(
    val id           : Int,
    val username     : String,
    val gender      : String? = null,
    val avatarUrl    : String? = null,
    val date         : Date,
    val lastActivity : Date
)
