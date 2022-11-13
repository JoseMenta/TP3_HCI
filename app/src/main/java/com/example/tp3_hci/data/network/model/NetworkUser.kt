package com.example.api_fiti.data.network.model

import com.example.tp3_hci.data.model.User
import com.google.gson.annotations.SerializedName
import java.util.*

data class NetworkUser(
    @SerializedName("id"           ) var id           : Int?     = null,
    @SerializedName("username"     ) var username     : String,
    @SerializedName("firstName"    ) var firstName    : String,
    @SerializedName("lastName"     ) var lastName     : String,
    @SerializedName("gender"       ) var gender       : String?  = null,
    @SerializedName("birthdate"    ) var birthdate    : Date?     = null,
    @SerializedName("email"        ) var email        : String,
    @SerializedName("phone"        ) var phone        : String?  = null,
    @SerializedName("avatarUrl"    ) var avatarUrl    : String?  = null,
    @SerializedName("metadata"     ) var metadata     : String?  = null, //hacer data class NetworkUserMetadata si la usamos
    @SerializedName("date"         ) var date         : Date?     = null,
    @SerializedName("lastActivity" ) var lastActivity : Date?     = null,
    @SerializedName("verified"     ) var verified     : Boolean? = null
){
    fun asModel():User{
        return User(
            id = id,
            username = username,
            firstName = firstName,
            lastName = lastName,
            email = email,
            lastActivity = lastActivity
        )
    }
}
