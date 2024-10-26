package com.example.androidfundamentalsubmission1

import android.media.metrics.Event
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidfundamentalsubmission1.response.DetailResponse
import com.example.androidfundamentalsubmission1.response.EventDetail
import com.example.androidfundamentalsubmission1.response.GetEventResponse
import com.example.androidfundamentalsubmission1.response.ListEventsItem
import com.example.androidfundamentalsubmission1.services.Upcomming
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit


class DetailActivityModel: ViewModel() {

    private val _event = MutableLiveData<EventDetail?>()
    val event: MutableLiveData<EventDetail?> get() = _event

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

     fun fetchEventDetail(eventId: Int) {
        val Timeout = 20000L
        val startTime = System.currentTimeMillis()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://event-api.dicoding.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(Upcomming::class.java)
        service.getDetail(eventId).enqueue(object : Callback<DetailResponse> {
            override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                if (System.currentTimeMillis() - startTime >= Timeout) {
                    Log.d("DetailActivityModel", "Request took more than $Timeout seconds")
                    _loading.value = false
                    return
                }
                if (response.isSuccessful) {
                    _event.value = response.body()?.event
                    _loading.value = false
                } else {
                    Log.d("DetailActivityModel", "Failed to fetch data ${response.message()}")
                    _loading.value = false
                }

            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.d("DetailActivityModel", "Failed to fetch data ${t.message}")
                _loading.value = false
            }
        })
    }
}