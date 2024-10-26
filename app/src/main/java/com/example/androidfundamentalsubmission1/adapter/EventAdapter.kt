package com.example.androidfundamentalsubmission1.adapter

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
import com.example.androidfundamentalsubmission1.model.eventUpcomming
import com.example.androidfundamentalsubmission1.ui.dashboard.DashboardFragment

class EventAdapter(val listEvent: List<eventUpcomming>): RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    class EventViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imgView: ImageView = itemView.findViewById(R.id.event_image)
        val titleView: TextView = itemView.findViewById(R.id.event_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_card, parent, false)
        return EventViewHolder(itemView)
    }

    override fun getItemCount() = listEvent.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val currentItem = listEvent[position]
        Glide.with(holder.itemView.context)
            .load(currentItem.imageUrl)
            .into(holder.imgView)
        holder.titleView.text = currentItem.title

        holder.itemView.setOnClickListener{
            val context = holder.itemView.context
            Log.d("EventAdapter", "Event ID: ${currentItem.id}")
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("EVENT_ID", currentItem.id)
            }
            context.startActivity(intent)
        }

    }
}