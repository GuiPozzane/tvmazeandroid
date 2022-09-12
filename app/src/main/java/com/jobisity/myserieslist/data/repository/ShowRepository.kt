package com.jobisity.myserieslist.data.repository


import android.R
import android.app.Application
import com.jobisity.myserieslist.data.remote.ITvMazeApi
import com.jobisity.myserieslist.domain.model.show.Show
import com.jobisity.myserieslist.domain.repository.IShowRepository
import retrofit2.awaitResponse
import javax.inject.Inject


class ShowRepository @Inject constructor(private val api:ITvMazeApi,    private val appContext: Application) : IShowRepository {
    override suspend fun getShows(page: Int): List<Show>? {
        var response = api.getShows(page).awaitResponse();
        if (response.isSuccessful)
            return response.body()!!;
        return null;
    }

    override suspend fun getShows(search: String): List<Show>? {
        var response = api.getShows(search).awaitResponse();
        if (response.isSuccessful)
            return response.body()?.map { it.show };
        return null;
    }

    override suspend fun getShowDetail(id: Int): Show? {
        var response = api.getShow(id,listOf("episodes")).awaitResponse();
        if (response.isSuccessful)
            return response.body()!!;
        return null;
    }

}