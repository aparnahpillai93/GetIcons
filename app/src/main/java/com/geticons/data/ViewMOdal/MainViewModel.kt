package com.geticons.data.ViewMOdal

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.geticons.data.Repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){

    private val mainRepo = MainRepository()
    fun searchIcon(
        context: Context,
        term:String,
        count:Int
    ) = liveData(Dispatchers.IO) {
        emit(mainRepo.searchicon(context,term,count))
    }

    fun getIconSets(
        count:Int,
        after:Int?
    ) = liveData(Dispatchers.IO) {
        emit(mainRepo.getIconSets(count,after))
    }


    fun getIconSetDetail(
        id:Int
    ) = liveData(Dispatchers.IO) {
        emit(mainRepo.getIconSetDetail(id))
    }

    fun getIcons_IconSet(
        id:Int
    ) = liveData(Dispatchers.IO) {
        emit(mainRepo.getIcons_IconSet(id))
    }

    fun getIconDetail(
        id:Int
    ) = liveData(Dispatchers.IO) {
        emit(mainRepo.getIconDetail(id))
    }

    fun getAuthorDetail(
        id:Int
    ) = liveData(Dispatchers.IO) {
        emit(mainRepo.getAuthorDetail(id))
    }
    fun getAuthorIconSets(
        id:Int
    ) = liveData(Dispatchers.IO) {
        emit(mainRepo.getAuthorIconSets(id))
    }
}