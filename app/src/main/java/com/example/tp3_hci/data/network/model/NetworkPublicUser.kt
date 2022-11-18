package com.example.tp3_hci.data.network.model

import com.example.tp3_hci.data.model.PublicUser
import com.google.gson.annotations.SerializedName
import java.util.*

data class NetworkPublicUser(
    @SerializedName("id"           ) var id           : Int,
    @SerializedName("username"     ) var username     : String,
    @SerializedName("gender"   )     var gender      : String? = null,
    @SerializedName("avatarUrl"    ) var avatarUrl    : String? = null,
    @SerializedName("date"         ) var date         : Date,
    @SerializedName("lastActivity" ) var lastActivity : Date
){
    fun toModel():PublicUser{
        return PublicUser(
            id = id,
            username = username,
            gender = gender,
            avatarUrl = avatarUrl,
            date = date,
            lastActivity = lastActivity
        )
    }
}
