package com.example.adoptapet.main

import android.app.Application
import com.example.adoptapet.models.AdoptJSONStore
import com.example.adoptapet.models.AdoptMemStore
import com.example.adoptapet.models.AdoptStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    //val adoptions = ArrayList<AdoptModel>()
    lateinit var adoptions: AdoptStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        adoptions = AdoptJSONStore(applicationContext)
        i("AdoptAPet started")

        //Listing tests

        // adoptions.add(AdoptModel("Max", "Border Collie..."))
        // adoptions.add(AdoptModel("Ser Charles", "Important cat..."))
        // adoptions.add(AdoptModel("Missy", "Poodle..."))
    }
}