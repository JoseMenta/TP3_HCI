package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class NetworkPublicUser(
    @SerializedName("id"           ) var id           : Int?    = null,
    @SerializedName("username"     ) var username     : String? = null,
    @SerializedName("gender"   )     var gender      : String? = null,
    @SerializedName("avatarUrl"    ) var avatarUrl    : String? = null,
    @SerializedName("date"         ) var date         : Date?    = null,
    @SerializedName("lastActivity" ) var lastActivity : Date?    = null
)
