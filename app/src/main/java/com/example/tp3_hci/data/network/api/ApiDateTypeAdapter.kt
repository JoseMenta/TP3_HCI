package com.example.tp3_hci.data.network.api

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.Date

class ApiDateTypeAdapter: TypeAdapter<Date?>() {
    override fun read(`in`: JsonReader): Date {
        //para pasar de timestamp a Date
        return Date(`in`.nextLong())
    }

    override fun write(out: JsonWriter, value: Date?) {
        //para pasar de Date a timestamp
        if(value==null){
            out.nullValue()
        }else{
            out.value(value.time)
        }
    }
}