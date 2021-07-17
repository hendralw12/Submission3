package com.example.submission1.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListTVResponse(
    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("total_pages")
    val totalPages: Int,

    @field:SerializedName("results")
    val results: List<TVResponse>,

    @field:SerializedName("total_results")
    val totalResults: Int
)



