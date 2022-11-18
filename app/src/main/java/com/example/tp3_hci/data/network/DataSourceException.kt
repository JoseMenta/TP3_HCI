package com.example.tp3_hci.data.network

class DataSourceException(
    val code: Int,
    message: String,
    val details: List<String>?,
    val stringResourceCode: Int
) : Exception(message)