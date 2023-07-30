package top.oxff.pieces.year

import top.oxff.pieces.database.dataModel.PieceEarningsByMonth
import java.time.LocalDateTime
import java.time.Year

data class PieceEarningsByYearState (
    val year: Year = Year.now(),
    val  startAndEndDate: Pair<LocalDateTime, LocalDateTime> = Pair(LocalDateTime.now(), LocalDateTime.now()),
    val productsByYear: List<PieceEarningsByMonth> = emptyList()
)