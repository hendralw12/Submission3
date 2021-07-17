package com.example.submission1.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    var id:Int,
    var judul:String,
    var desc:String,
    var type:String,
    var image:String,
    var isFavorite:Boolean
    ):Parcelable
