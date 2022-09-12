package com.jobisity.myserieslist.data.remote

import com.jobisity.myserieslist.domain.model.show.SearchShow
import com.jobisity.myserieslist.domain.model.show.Show
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ITvMazeApi {
    @GET("shows")
    fun getShows(@Query("page") page:Int?) : Call<List<Show>>
    @GET("shows/{id}")
    fun getShow(@Path("id") id:Int,@Query("embed") embed:List<String>) : Call<Show>

    @GET("search/shows")
    fun getShows(@Query("q") search:String) : Call<List<SearchShow>>
}