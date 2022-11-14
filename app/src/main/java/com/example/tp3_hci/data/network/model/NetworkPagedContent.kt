package com.example.api_fiti.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkPagedContent <T>(
    @SerializedName("totalCount" ) var totalCount : Int,
    @SerializedName("orderBy"    ) var orderBy    : String?            = null,
    @SerializedName("direction"  ) var direction  : String?            = null,
    @SerializedName("content"    ) var content    : ArrayList<T> = arrayListOf(),
    @SerializedName("size"       ) var size       : Int?               = null,
    @SerializedName("page"       ) var page       : Int?               = null,
    @SerializedName("isLastPage" ) var isLastPage : Boolean
)
