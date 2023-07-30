package top.oxff.pieces.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import top.oxff.pieces.Converter.BigDecimalTypeConverter
import top.oxff.pieces.Converter.DateConverter
import top.oxff.pieces.database.dao.PieceDao
import top.oxff.pieces.database.dataModel.Piece

@Database(entities = [Piece::class], version = 4, exportSchema = false)
@TypeConverters(
    DateConverter::class,
    BigDecimalTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao: PieceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "pieces.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}