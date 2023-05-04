package com.example.adoptapet.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

@Parcelize
data class AdoptModel(var id: Long = 0,
                      var title: String = "",
                      var description: String = "",
                      var petAge: Int = 0,
                      var email: String = "",
                      var availableDate: String = "",
                      var image: Uri = Uri.EMPTY,
                      var lat: Double = 0.0,
                      var lng: Double = 0.0,
                      var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

