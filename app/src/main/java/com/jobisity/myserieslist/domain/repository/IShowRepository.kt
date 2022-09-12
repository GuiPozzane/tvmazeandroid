package com.jobisity.myserieslist.domain.repository
import com.jobisity.myserieslist.domain.model.show.*


interface IShowRepository {
    suspend fun getShows(page:Int) : List<Show>?
    suspend fun getShows(search:String) : List<Show>?
    suspend fun getShowDetail(id:Int) : Show?
}