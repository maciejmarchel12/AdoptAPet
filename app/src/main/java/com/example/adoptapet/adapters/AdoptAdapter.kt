package com.example.adoptapet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adoptapet.databinding.CardAdoptBinding
import com.example.adoptapet.models.AdoptModel
import com.squareup.picasso.Picasso

interface AdoptListener {
    fun onAdoptClick(adopt: AdoptModel)
}

class AdoptAdapter constructor(private var adoptions: List<AdoptModel>,
                               private val listener: AdoptListener) :
    RecyclerView.Adapter<AdoptAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardAdoptBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val adopt = adoptions[holder.adapterPosition]
        holder.bind(adopt, listener)
    }

    override fun getItemCount(): Int = adoptions.size

    //Card displaying user input

    class MainHolder(private val binding : CardAdoptBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(adopt: AdoptModel, listener: AdoptListener) {
            binding.adoptTitle.text = adopt.title
            binding.description.text = adopt.description
            binding.email.text = adopt.email
            binding.age.text = adopt.petAge.toString()
            binding.date.text = adopt.availableDate
            Picasso.get().load(adopt.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onAdoptClick(adopt) }
        }
    }
}