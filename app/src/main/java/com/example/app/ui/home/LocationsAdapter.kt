package com.example.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.ListItemHomeBinding
import com.example.app.model.Locations
import com.squareup.picasso.Picasso

class LocationsAdapter : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    var data = listOf<Locations>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    lateinit var onItemClicked: OnItemClicked

    inner class ViewHolder(val binding: ListItemHomeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsAdapter.ViewHolder {
        val binding = ListItemHomeBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: LocationsAdapter.ViewHolder, position: Int) {
        val item = data[position]
        with(holder) {
            binding.nameHome.text = item.name
            binding.typeHome.text = item.type
            binding.reviewHome.text = item.review.toString()
            binding.ratingBar.rating = item.review
            binding.cardHome.setOnClickListener {
                onItemClicked.onClick(item.id, item.img)
            }

            val imageUri = (item.img ?: "").toUri().buildUpon().scheme("https").build()
            Picasso.get()
                .load(imageUri)
                .into(binding.imageHome)
        }
    }
}

interface OnItemClicked {
    fun onClick(id: Int, oneImage: String?)
}