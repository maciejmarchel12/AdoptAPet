package com.example.adoptapet.models

interface AdoptStore {
    fun findAll(): List<AdoptModel>
    fun create(adopt: AdoptModel)
    fun update(adopt: AdoptModel)

    fun delete(adopt: AdoptModel)

    fun findById(id:Long) : AdoptModel?
}