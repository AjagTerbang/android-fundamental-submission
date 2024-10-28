package com.example.androidfundamentalsubmission1

import android.content.Intent
import android.media.metrics.Event
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.androidfundamentalsubmission1.database.AppDatabase
import com.example.androidfundamentalsubmission1.databinding.ActivityDetailBinding
import com.example.androidfundamentalsubmission1.entity.FavoriteEvent
import com.example.androidfundamentalsubmission1.model.eventUpcomming
import com.example.androidfundamentalsubmission1.response.EventDetail
import com.example.androidfundamentalsubmission1.response.ListEventsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityDetailBinding
    private val detailActivityModel : DetailActivityModel by viewModels()
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(applicationContext)

        val eventId = intent.getIntExtra("EVENT_ID", -1)
        Log.d("DetailActivityDebug", "Event ID: $eventId")

        if (eventId != -1) {
            detailActivityModel.fetchEventDetail(eventId)
            observeFavoriteStatus(eventId.toString())
        }

        detailActivityModel.event.observe(this, Observer { event ->
            event?.let {
                updateUI(it)
            } ?: run {
                Toast.makeText(this, "Failed to load event details", Toast.LENGTH_SHORT).show()
            }
        })


        detailActivityModel.loading.observe(this, Observer { isLoading ->
            binding.detailProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })


        binding.loveButton.setOnClickListener(this)



    }

    private fun observeFavoriteStatus(eventId: String) {
        database.favoriteEventDao().getFavoriteEventByIdLive(eventId).observe(this, Observer { favoriteEvent ->
            if (favoriteEvent != null) {
                binding.loveButton.setImageResource(R.drawable.ic_is_favorite)
            } else {
                binding.loveButton.setImageResource(R.drawable.ic_is_not_favorite_dark)
            }
        })
    }
    private fun updateUI(event: EventDetail) {
        Glide.with(this)
            .load(event.mediaCover)
            .into(binding.detailImg)
        binding.detailTitle.text = event.name
        binding.detailPenyelenggara.text = event.ownerName
        binding.detailWaktu.text = "Waktu: ${event.beginTime}"
        binding.detailQuota.text = String.format("Sisa Kuota: %s", event.quota-event.registrants)
        binding.detailIsiDeskripsi.text =
            event.description?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_COMPACT) }

       binding.detailRegister.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
            startActivity(intent)

       }
    }



    override fun onClick(view: View?) {
       when(view?.id){
           R.id.love_button -> {
               val event = detailActivityModel.event.value
               event?.let {
                   val favoriteEvent = FavoriteEvent(
                       id = it.id.toString(),
                       name = it.name,
                       mediaCover = it.mediaCover,
                   )
                   CoroutineScope(Dispatchers.IO).launch {
                       val existingEvent = database.favoriteEventDao().getFavoriteEventById(it.id.toString())

                       if(existingEvent != null){
                           database.favoriteEventDao().deleteFavoriteEventById(it.id.toString())
                            Log.d("ActivityDetailDebug", "Event removed from favorite")

                       }else{
                           database.favoriteEventDao().insertFavoriteEvent(favoriteEvent)
                            Log.d("ActivityDetailDebug", "Event added to favorite")

                       }
                       val allFavorite = database.favoriteEventDao().getAllFavoriteEvent()
                          Log.d("ActivityDetailDebug", "All favorite events: $allFavorite")
                   }

               }
           }
       }
    }
}