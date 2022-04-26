package com.interview.diagnal

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import javax.inject.Inject

class FileDB @Inject constructor(@ApplicationContext val context: Context) {
    fun getDataOfPage(pageNo: Int): String {
        var fileName = "page$pageNo.json"
        return try {
            context.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
        }catch (e: Exception){
            ""
        }
    }
}