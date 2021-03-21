package com.example.app.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.example.app.databinding.ListItemDetailsBinding
import com.example.app.model.Images

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    var imagesArray = Images()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(val binding: ListItemDetailsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemDetailsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = imagesArray.hits[position].webformatURL
        with(holder){
            val imageUri = item.toUri().buildUpon().scheme("https").build()
            Picasso.get()
                .load(imageUri)
                .into(binding.imageRecyclerDetails as ImageView)
        }
    }

    override fun getItemCount(): Int {
        return imagesArray.hits.size
    }
}