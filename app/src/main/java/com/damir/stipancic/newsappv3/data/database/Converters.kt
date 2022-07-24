package com.damir.stipancic.newsappv3.data.database

import androidx.room.TypeConverter
import com.damir.stipancic.newsappv3.data.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}