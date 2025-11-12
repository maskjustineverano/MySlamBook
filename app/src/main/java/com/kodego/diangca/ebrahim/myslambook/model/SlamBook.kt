package com.kodego.diangca.ebrahim.myslambook.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "slambook_table")
@TypeConverters(Converters::class)
data class SlamBook(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var fullName: String? = null,
    var nickName: String? = null,
    var friendCall: String? = null,
    var likeToCall: String? = null,
    var birthDate: String? = null,
    var gender: String? = null,
    var status: String? = null,
    var emailAdd: String? = null,
    var contactNo: String? = null,
    var address: String? = null,

    var favoriteSongs: List<String>? = null,
    var favoriteMovies: List<String>? = null,
    var hobbies: List<String>? = null,
    var skillsWithRate: List<Skill>? = null,

    var defineLove: String? = null,
    var defineFriendship: String? = null,
    var memorableExperience: String? = null,
    var describeMe: String? = null,
    var adviceForMe: String? = null,
    var motto: String? = null,
    var rating: Float = 0f,
    var imageUri: String? = null

) : Parcelable