package com.example.adoptapet.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdoptModel(var id: Long = 0,
                      var title: String = "",
                      var description: String = "") : Parcelable



