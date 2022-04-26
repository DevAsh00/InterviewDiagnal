package com.interview.diagnal

import com.interview.diagnal.utils.convertArrObjDataFromJson
import org.json.JSONObject
import javax.inject.Inject

class MovieRepository @Inject constructor(private val dbObj: FileDB) {

    fun getMovies(pageNo: Int) :List<MovieModel>{
        val strData = dbObj.getDataOfPage(pageNo)
        return if (strData.isNotEmpty()) {
            val json = JSONObject(strData).getJSONObject("page").getJSONObject("content-items")
                .getJSONArray("content")
            convertArrObjDataFromJson(json.toString())
        }else
            listOf()
    }

    /*suspend fun getMoviesNetwork(pageNo: Int) {
        dbObj.getDataOfPage(pageNo)
    }*/
}