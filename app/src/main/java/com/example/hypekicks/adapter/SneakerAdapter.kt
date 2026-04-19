package com.example.hypekicks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hypekicks.databinding.ItemSneakerBinding
import com.example.hypekicks.model.Sneaker


class SneakerAdapter(
    private var sneakers: List<Sneaker>,
    private val onItemClick: (Sneaker) -> Unit
) : RecyclerView.Adapter<SneakerAdapter.SneakerViewHolder>() {

    class SneakerViewHolder(val binding: ItemSneakerBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SneakerViewHolder {
        val binding = ItemSneakerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SneakerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SneakerViewHolder, position: Int) {
        val sneaker = sneakers[position]

        holder.binding.tvSneakerBrand.text = sneaker.brand
        holder.binding.tvSneakerModel.text = sneaker.modelName

        Glide.with(holder.itemView.context)
            .load(sneaker.imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.binding.ivSneaker)

        holder.itemView.setOnClickListener {
            onItemClick(sneaker)
        }
    }

    override fun getItemCount(): Int = sneakers.size

    fun updateData(newList: List<Sneaker>) {
        sneakers = newList
        notifyDataSetChanged()
    }
}