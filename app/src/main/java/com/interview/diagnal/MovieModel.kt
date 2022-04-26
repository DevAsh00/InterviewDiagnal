package com.interview.diagnal

import com.google.gson.annotations.SerializedName

data class MovieModel(val name: String, @SerializedName("poster-image") val posterImage: String)
