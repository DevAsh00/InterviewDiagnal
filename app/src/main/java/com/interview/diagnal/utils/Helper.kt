package com.interview.diagnal.utils

import android.widget.ImageView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.interview.diagnal.R

fun ImageView.loadImage(imgPath: String) {
    val resId = resources.getIdentifier(imgPath.split(".")[0], "drawable", context.packageName)
    setImageResource(if (resId==0) R.drawable.placeholder else resId)
}

inline fun <reified T> convertArrObjDataFromJson(json: String?): T {
    return Gson().fromJson<T>(json, object : TypeToken<T>() {}.type)
}