package com.kodego.diangca.ebrahim.myslambook.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SlamBookViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SlamBookRepository
    val allSlamBookEntries: LiveData<List<SlamBook>>

    init {
        val slamBookDao = SlamBookDatabase.getDatabase(application).slamBookDao()
        repository = SlamBookRepository(slamBookDao)
        allSlamBookEntries = repository.allSlamBookEntries
    }

    fun insertSlamBook(slamBook: SlamBook) = viewModelScope.launch {
        repository.insert(slamBook)
    }

    fun updateSlamBook(slamBook: SlamBook) = viewModelScope.launch {
        repository.update(slamBook)
    }

    fun deleteSlamBook(slamBook: SlamBook) = viewModelScope.launch {
        repository.delete(slamBook)
    }

    fun getSlamBookEntry(id: Int): LiveData<SlamBook> {
        return repository.getSlamBookEntry(id)
    }
}