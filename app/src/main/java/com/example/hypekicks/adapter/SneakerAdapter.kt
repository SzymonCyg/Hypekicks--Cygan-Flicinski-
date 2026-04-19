package com.example.hypekicks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hypekicks.R
import com.example.hypekicks.model.Sneaker

class SneakerAdapter(
    private var sneakers: List<Sneaker>,
    private val onItemClick: (Sneaker) -> Unit
) : RecyclerView.Adapter<SneakerAdapter.SneakerViewHolder>() {

    class SneakerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivSneaker: ImageView = itemView.findViewById(R.id.ivSneaker)
        val tvBrand: TextView = itemView.findViewById(R.id.tvSneakerBrand)
        val tvModel: TextView = itemView.findViewById(R.id.tvSneakerModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SneakerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sneaker, parent, false)

        return SneakerViewHolder(view)
    }

    override fun onBindViewHolder(holder: SneakerViewHolder, position: Int) {
        val sneaker = sneakers[position]

        holder.tvBrand.text = sneaker.brand
        holder.tvModel.text = sneaker.modelName

        Glide.with(holder.itemView.context)
            .load(sneaker.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.ivSneaker)

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