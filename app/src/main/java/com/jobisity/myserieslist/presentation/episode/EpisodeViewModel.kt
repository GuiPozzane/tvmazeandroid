package com.jobisity.myserieslist.presentation.episode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jobisity.myserieslist.domain.model.show.Episode

class EpisodeViewModel : ViewModel() {
    val episode = MutableLiveData<Episode>()
}