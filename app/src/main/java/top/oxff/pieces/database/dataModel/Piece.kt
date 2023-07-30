package top.oxff.pieces.database.dataModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import top.oxff.pieces.Converter.DateConverter
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(tableName = "pieces")
data class Piece(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val price: BigDecimal,
    val quantity: Int,
    @TypeConverters(DateConverter::class)
    val dateTime: LocalDateTime
) {
    companion object
}