package com.example.tp3_hci.data.network

import android.util.Log
import com.example.tp3_hci.data.network.model.NetworkError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.io.IOException
import com.example.tp3_hci.R

abstract class RemoteDataSource {
    private val errorCodeToMessage: HashMap<Int,Int> =
        hashMapOf(0 to R.string.unexpected_error,
            1 to R.string.invalid_data_error,
            2 to R.string.data_constraint_error,
            3 to R.string.not_found_error,
            4 to R.string.invalid_username_or_password,
            5 to R.string.database_error,
            6 to R.string.invalid_request,
            7 to R.string.unauthorized,
            8 to R.string.email_verification_error,
            9 to R.string.forbidden_error
        )
    suspend fun <T : Any> handleApiResponse(
        execute: suspend () -> Response<T>
    ): T {
        try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                return body
            }
            response.errorBody()?.let {
                val gson = Gson()
                val error = gson.fromJson<NetworkError>(it.string(), object : TypeToken<NetworkError?>() {}.type)
                throw DataSourceException(error.code, error.description, error.details,errorCodeToMessage[error.code]?:R.string.unexpected_error)
            }
            throw DataSourceException(UNEXPECTED_ERROR_CODE, "Missing error", null,R.string.unexpected_error)
        } catch (e: DataSourceException) {
            throw e
        } catch (e: IOException) {
            throw DataSourceException(CONNECTION_ERROR_CODE, "Connection error", getDetailsFromException(e), R.string.no_connection_error)
        } catch (e: Exception) {
            Log.e("Exception",e.message?:"No hay mensaje")
            throw DataSourceException(UNEXPECTED_ERROR_CODE, "Unexpected error", getDetailsFromException(e), R.string.unexpected_error)
        }
    }

    private fun getDetailsFromException(e: Exception) : List<String> {
        return if (e.message != null) listOf(e.message!!) else emptyList()
    }

    companion object {
        const val CONNECTION_ERROR_CODE = 98
        const val UNEXPECTED_ERROR_CODE = 99
    }
}