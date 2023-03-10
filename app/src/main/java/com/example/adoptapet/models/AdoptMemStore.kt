package com.example.adoptapet.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class AdoptMemStore : AdoptStore {

    val adoptions = ArrayList<AdoptModel>()

    override fun findAll(): List<AdoptModel> {
        return adoptions
    }

    override fun create(adopt: AdoptModel) {
        adopt.id = getId()
        adoptions.add(adopt)
        logAll()
    }

    //Code for editing the listing

    override fun update(adopt: AdoptModel) {
        var foundAdopt: AdoptModel? = adoptions.find { a -> a.id == adopt.id }
        if (foundAdopt != null) {
            foundAdopt.title = adopt.title
            foundAdopt.description = adopt.description
            foundAdopt.email = adopt.email
            foundAdopt.petAge = adopt.petAge
            foundAdopt.availableDate = adopt.availableDate
            logAll()
        }
    }

    private fun logAll() {
        adoptions.forEach { i("$it") }
    }
}