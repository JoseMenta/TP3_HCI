package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName

//Full Cycle
data class NetworkCycle(
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("detail"      ) var detail      : String? = null,
    @SerializedName("type"        ) var type        : String? = null,
    @SerializedName("order"       ) var order       : Int?    = null,
    @SerializedName("repetitions" ) var repetitions : Int?    = null,
    @SerializedName("metadata"    ) var metadata    : String? = null
)
