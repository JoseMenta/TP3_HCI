package com.example.tp3_hci.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkCycleMetadata(
    @SerializedName("deletable"        ) var deletable        : String? = null,
)
