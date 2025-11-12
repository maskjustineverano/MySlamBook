package com.kodego.diangca.ebrahim.myslambook.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        if (value == null) {
            return null
        }
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromSkillList(skills: List<Skill>?): String? {
        return gson.toJson(skills)
    }

    @TypeConverter
    fun toSkillList(skillsJson: String?): List<Skill>? {
        if (skillsJson == null) {
            return null
        }
        val type = object : TypeToken<List<Skill>>() {}.type
        return gson.fromJson(skillsJson, type)
    }
}