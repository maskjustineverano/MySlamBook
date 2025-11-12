package com.kodego.diangca.ebrahim.myslambook.model

import androidx.lifecycle.LiveData

class SlamBookRepository(private val slamBookDao: SlamBookDao) {

    val allSlamBookEntries: LiveData<List<SlamBook>> = slamBookDao.getAllSlamBookEntries()

    suspend fun insert(slamBook: SlamBook) {
        slamBookDao.insertSlamBook(slamBook)
    }

    suspend fun update(slamBook: SlamBook) {
        slamBookDao.updateSlamBook(slamBook)
    }

    suspend fun delete(slamBook: SlamBook) {
        slamBookDao.deleteSlamBook(slamBook)
    }

    fun getSlamBookEntry(id: Int): LiveData<SlamBook> {
        return slamBookDao.getSlamBookEntry(id)
    }
}