package com.chalo.assignments.omdbcarousal.home.home

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chalo.assignments.omdbcarousal.databinding.ItemMediaBinding
import com.chalo.assignments.omdbcarousal.home.repository.models.Media

class MediaViewHolder(private val binding: ItemMediaBinding): ViewHolder(binding.root) {

    fun bind(item: Media){
        binding.model = item
    }

}