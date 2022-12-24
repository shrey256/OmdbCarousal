package com.chalo.assignments.omdbcarousal.home.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chalo.assignments.omdbcarousal.databinding.ItemMediaBinding
import com.chalo.assignments.omdbcarousal.home.repository.models.Media
import javax.inject.Inject

class MediaViewPagerAdapter @Inject constructor(): RecyclerView.Adapter<MediaViewHolder>() {

    private val list:  ArrayList<Media> = ArrayList()

    fun addAll(newItems : List<Media>){
        var startIndex = list.size
        list.addAll(newItems)
        notifyItemRangeInserted(startIndex, newItems.size)
    }

    fun addItem(newItem: Media){
        list.add(newItem)
        notifyItemInserted(list.indexOf(newItem))
    }

    fun removeItem(item: Media){
        if(!list.contains(item)){
            return
        }
        var index = list.indexOf(item)
        list.remove(item)
        notifyItemRemoved(index)
    }

    fun replaceList(newList: List<Media>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        return MediaViewHolder(ItemMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}