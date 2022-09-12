package com.jobisity.myserieslist.presentation.showdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jobisity.myserieslist.domain.model.show.Show
import com.jobisity.myserieslist.domain.repository.IShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailViewModel @Inject constructor(val showRepository: IShowRepository) : ViewModel() {
    val show = MutableLiveData<Show>()
    fun loadDetail(id:Int) {
        GlobalScope.launch {
            async {
                var response = showRepository.getShowDetail(id);
                show?.postValue(response!!);
            }
        }

    }
}