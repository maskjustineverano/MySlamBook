package com.kodego.diangca.ebrahim.myslambook.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kodego.diangca.ebrahim.myslambook.model.SlamBook

@Dao
interface SlamBookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSlamBook(slamBook: SlamBook)

    @Query("SELECT * FROM slambook_table ORDER BY id DESC")
    suspend fun readAllSlamBook(): List<SlamBook>

}
