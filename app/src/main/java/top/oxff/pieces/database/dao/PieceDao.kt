package top.oxff.pieces.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import top.oxff.pieces.database.dataModel.Piece
import top.oxff.pieces.database.dataModel.PieceEarningsByDay
import top.oxff.pieces.database.dataModel.PieceEarningsByMonth
import java.math.BigDecimal
import java.time.LocalDateTime


@Dao
interface PieceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPiece(piece: Piece)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPieces(pieces: List<Piece>)

    @Delete
    fun deletePiece(piece: Piece)

    // update product
    @Query("UPDATE pieces SET price = :price, quantity = :quantity, dateTime = :dateTime WHERE id = :id")
    fun updatePiece(id: Int, price: BigDecimal, quantity: Int, dateTime: LocalDateTime)

    @Query("SELECT * FROM pieces WHERE DATE(dateTime) = :targetDate")
    fun getPiecesByDate(targetDate: String): Flow<List<Piece>>

    @Query("SELECT strftime('%Y-%m-%d', dateTime) AS day, SUM(price * quantity) AS total FROM pieces WHERE date(dateTime) BETWEEN :startDate AND :endDate GROUP BY day ORDER BY day DESC")
    fun getPieceEarningsBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<PieceEarningsByDay>>

    @Query("SELECT strftime('%Y-%m', dateTime) AS month, SUM(price * quantity) AS total FROM pieces WHERE date(dateTime) BETWEEN :startDate AND :endDate GROUP BY month ORDER BY month DESC")
    fun getSalesBetweenDatesByMonth(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<PieceEarningsByMonth>>

}