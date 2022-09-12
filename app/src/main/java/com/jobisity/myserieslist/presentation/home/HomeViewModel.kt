package com.jobisity.myserieslist.presentation.home

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
class HomeViewModel @Inject constructor(val showRepository: IShowRepository) : ViewModel() {
    val shows = MutableLiveData<List<Show>>()
    var  currentPage = MutableLiveData<Int> (0)
    init {
        loadList(0);

    }
    fun nextPage(){
        var value = currentPage.value;
        loadList(value!! + 1);
    }
    fun backPage(){
        var value = currentPage.value;
        if(value!! <= 0)
            return;

        loadList(value!! - 1);
    }
    fun loadList(page : Int) {
         GlobalScope.launch {
             async {
                 currentPage.postValue(page)
                 var response = showRepository.getShows(page)?.toMutableList();
                 if(response != null)
                    shows?.postValue(response!!);
             }
         }

    }
    fun searchShow(search : String) {
        GlobalScope.launch {
            async {
                var response = showRepository.getShows(search)?.toMutableList();
                shows?.postValue(response!!);
            }
        }

    }
}