package com.example.adoptapet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adoptapet.databinding.CardAdoptBinding
import com.example.adoptapet.models.AdoptModel

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

    class MainHolder(private val binding : CardAdoptBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(adopt: AdoptModel, listener: AdoptListener) {
            binding.adoptTitle.text = adopt.title
            binding.description.text = adopt.description
            binding.root.setOnClickListener { listener.onAdoptClick(adopt) }
        }
    }
}