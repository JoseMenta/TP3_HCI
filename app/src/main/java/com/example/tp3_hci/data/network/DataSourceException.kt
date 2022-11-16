package com.example.tp3_hci.data.network

class DataSourceException(
    code: Int,
    message: String,
    details: List<String>?
) : Exception(message)