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
import com.bumptech.glide.Glide
import com.example.androidfundamentalsubmission1.databinding.ActivityDetailBinding
import com.example.androidfundamentalsubmission1.model.eventUpcomming
import com.example.androidfundamentalsubmission1.response.EventDetail
import com.example.androidfundamentalsubmission1.response.ListEventsItem

class DetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBinding
    private val detailActivityModel : DetailActivityModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventId = intent.getIntExtra("EVENT_ID", -1)
        Log.d("DetailActivityDebug", "Event ID: $eventId")

        if (eventId != -1) {
            detailActivityModel.fetchEventDetail(eventId)
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
}