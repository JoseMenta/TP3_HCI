package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName

//Full Cycle
data class NetworkCycle(
    @SerializedName("id"          ) var id          : Int,
    @SerializedName("name"        ) var name        : String,
    @SerializedName("detail"      ) var detail      : String,
    @SerializedName("type"        ) var type        : String,
    @SerializedName("order"       ) var order       : Int,
    @SerializedName("repetitions" ) var repetitions : Int,
    @SerializedName("metadata"    ) var metadata    : NetworkCycleMetadata? = null
)
