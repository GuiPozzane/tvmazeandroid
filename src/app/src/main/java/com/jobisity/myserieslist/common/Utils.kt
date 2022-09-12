package com.jobisity.myserieslist.common

import com.google.gson.Gson
import com.google.gson.GsonBuilder


class Utils {
    private var gson: Gson? = null

    fun getGsonParser(): Gson? {
        if (null == gson) {
            val builder = GsonBuilder()
            gson = builder.create()
        }
        return gson
    }
}