package com.example.androidfundamentalsubmission1.services

import com.example.androidfundamentalsubmission1.response.DetailResponse
import com.example.androidfundamentalsubmission1.response.GetEventResponse
import com.example.androidfundamentalsubmission1.response.ListEventsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Upcomming {
    @GET("https://event-api.dicoding.dev/events")
    fun getUpcomming(@Query("active") active:Int = 1 ): Call<GetEventResponse>

    @GET("https://event-api.dicoding.dev/events/{id}")
    fun getDetail(@Path("id") id:Int): Call<DetailResponse>


}

