package com.example.adoptapet.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

@Parcelize
data class AdoptModel(var id: Long = 0,
                      var title: String = "",
                      var description: String = "",
                      var petAge: Int = 0,
                      var email: String = "",
                      var availableDate: String = "") : Parcelable



