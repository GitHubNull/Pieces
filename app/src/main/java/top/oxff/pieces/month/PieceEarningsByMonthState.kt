package top.oxff.pieces.month

import top.oxff.pieces.database.dataModel.PieceEarningsByDay
import java.time.LocalDateTime
import java.time.YearMonth

data class PieceEarningsByMonthState(
//    val year: Year = Year.now(),
    val yearMonth: YearMonth = YearMonth.now(),
    val  startAndEndDate: Pair<LocalDateTime, LocalDateTime> = Pair(LocalDateTime.now(), LocalDateTime.now()),
    val salesByDay: List<PieceEarningsByDay> = emptyList()
)
