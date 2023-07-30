package top.oxff.pieces.Converter

import androidx.room.TypeConverter
import java.math.BigDecimal

object BigDecimalTypeConverter {
    @TypeConverter
    fun fromString(value: String): BigDecimal {
        return BigDecimal(value)
    }

    @TypeConverter
    fun toString(value: BigDecimal): String {
        return value.toString()
    }
}