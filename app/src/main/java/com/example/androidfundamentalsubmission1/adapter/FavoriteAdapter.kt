package com.example.androidfundamentalsubmission1.adapter

import android.app.usage.UsageEvents
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidfundamentalsubmission1.DetailActivity
import com.example.androidfundamentalsubmission1.R
import com.example.androidfundamentalsubmission1.entity.FavoriteEvent
import kotlin.math.log

class FavoriteEventAdapter(var favoriteEvents: List<FavoriteEvent>) : RecyclerView.Adapter<FavoriteEventAdapter.FavoriteEventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteEventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_card, parent, false)
        return FavoriteEventViewHolder(itemView)
    }



    class FavoriteEventViewHolder(binding: View): RecyclerView.ViewHolder(binding) {
        private val imgView: ImageView = binding.findViewById(R.id.event_image)
        private val titleView: TextView = binding.findViewById(R.id.event_title)

        fun bind(favoriteEvent: FavoriteEvent) {
            titleView.text = favoriteEvent.name
            Glide.with(itemView.context)
                .load(favoriteEvent.mediaCover)
                .into(imgView)
        }
    }

    override fun onBindViewHolder(
        holder: FavoriteEventAdapter.FavoriteEventViewHolder,
        position: Int
    ) {
        val currentItem = favoriteEvents[position]
       holder.bind(currentItem)
        Log.d("FavoriteEventAdapter", currentItem.id)
        holder.itemView.setOnClickListener{
            val context = holder.itemView.context
            Log.d("EventAdapterClicked", "Event ID: ${currentItem.id}")
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("EVENT_ID", currentItem.id.toInt())
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = favoriteEvents.size


    fun updateData(newEvents: List<FavoriteEvent>) {
        favoriteEvents = newEvents
        notifyDataSetChanged()
    }


}