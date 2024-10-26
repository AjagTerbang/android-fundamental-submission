package com.example.androidfundamentalsubmission1.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidfundamentalsubmission1.exception.OutOfTimeException
import com.example.androidfundamentalsubmission1.response.GetEventResponse
import com.example.androidfundamentalsubmission1.response.ListEventsItem
import com.example.androidfundamentalsubmission1.services.Upcomming
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HomeViewModel : ViewModel() {

    private val _events = MutableLiveData<List<ListEventsItem>>()
    private val _error = MutableLiveData<String>()
    val events: LiveData<List<ListEventsItem>> get() = _events
    val error: LiveData<String> get() = _error
    init {
        fetchEvents()
    }

    private fun  fetchEvents() {
        val Timeout = 20000L
        val startTime = System.currentTimeMillis()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://event-api.dicoding.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(Upcomming::class.java)
        service.getUpcomming().enqueue(object: Callback<GetEventResponse>{
            override fun onResponse(
                call: Call<GetEventResponse>,
                response: Response<GetEventResponse>
            ) {
              if(System.currentTimeMillis() - startTime >= TimeUnit.MILLISECONDS.toMillis(Timeout)){
                  _error.value = "Request Took more than $Timeout seconds"
                  return
              }

              if(response.isSuccessful){
                  _events.value = response.body()?.listEvents?.filterNotNull()
              }else {
                  _error.value = "Failed to fetch data ${response.message()}"
              }
            }

            override fun onFailure(call: Call<GetEventResponse>, t: Throwable) {
                _error.value = "Failed to fetch data ${t.message}"
            }

        })
    }


}