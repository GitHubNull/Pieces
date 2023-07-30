package top.oxff.pieces.Converter

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

object DateConverter {
    private val formatter: DateTimeFormatter by lazy {
        DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss")
            .toFormatter()
            .withZone(ZoneId.systemDefault())
    }

    @TypeConverter
    @JvmStatic
    fun fromString(value: String?): LocalDateTime? {
        return value?.let {
            LocalDateTime.parse(it, formatter)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromDate(date: LocalDateTime?): String? {
        return date?.format(formatter)
    }
}