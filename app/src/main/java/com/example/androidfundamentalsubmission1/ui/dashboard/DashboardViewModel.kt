package com.example.androidfundamentalsubmission1.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidfundamentalsubmission1.response.GetEventResponse
import com.example.androidfundamentalsubmission1.response.ListEventsItem
import com.example.androidfundamentalsubmission1.services.Upcomming
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DashboardViewModel : ViewModel() {

    private val _events = MutableLiveData<List<ListEventsItem>>()
    val events: LiveData<List<ListEventsItem>> = _events

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init{
        fetchEvents()
    }

    private fun fetchEvents(){
        val timeout = 20000L
        val startTime = System.currentTimeMillis()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://event-api.dicoding.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(Upcomming::class.java)
        service.getUpcomming(active = 0).enqueue(object : Callback<GetEventResponse>{
            override fun onResponse(
                call: Call<GetEventResponse>,
                response: Response<GetEventResponse>
            ) {
                if(System.currentTimeMillis() - startTime >= timeout){
                    _error.value = "Request took more than $timeout seconds"
                    return
                }
                if(response.isSuccessful){
                    _events.value = response.body()?.listEvents?.filterNotNull()
                }else{
                    _error.value = "Failed to fetch data ${response.message()}"
                }
            }

            override fun onFailure(call: Call<GetEventResponse>, t: Throwable) {
                _error.value = "Failed to fetch data ${t.message}"
            }

        })


    }



}