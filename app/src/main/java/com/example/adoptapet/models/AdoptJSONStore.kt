package com.example.adoptapet.models

import android.net.Uri
import android.content.Context
import com.example.adoptapet.helpers.*
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

const val JSON_FILE = "adoptions.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
                    .registerTypeAdapter(Uri::class.java, UriParser())
                    .create()
val listType: Type = object : TypeToken<ArrayList<AdoptModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class AdoptJSONStore(private val context: Context) : AdoptStore {

    var adoptions = mutableListOf<AdoptModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll() : MutableList<AdoptModel> {
        logAll()
        return adoptions
    }

    override fun create(adopt: AdoptModel) {
        adopt.id = generateRandomId()
        adoptions.add(adopt)
        serialize()
    }

    override fun update(adopt: AdoptModel) {
        val adoptionsList = findAll() as ArrayList<AdoptModel>
        var foundAdopt: AdoptModel? = adoptionsList.find { a -> a.id == adopt.id}
        if (foundAdopt != null) {
            foundAdopt.title = adopt.title
            foundAdopt.description = adopt.description
            foundAdopt.email = adopt.email
            foundAdopt.petAge = adopt.petAge
            foundAdopt.availableDate = adopt.availableDate
            foundAdopt.image = adopt.image
            foundAdopt.lat = adopt.lat
            foundAdopt.lng = adopt.lng
            foundAdopt.zoom = adopt.zoom
        }
        serialize()
    }

    override fun delete(adopt: AdoptModel) {
        adoptions.remove(adopt)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(adoptions, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        adoptions = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        adoptions.forEach { Timber.i("$it")}
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}