package top.oxff.pieces.month

import java.time.YearMonth

sealed interface PieceEarningsByMonthEvent{
//    object OnChangeByMonth: PieceEarningsByMonthEvent
//    object OnChangeYear: PieceEarningsByMonthEvent

    data class OnChangeByMonthValue(val yearMonth: YearMonth): PieceEarningsByMonthEvent
//    data class OnChangeYearValue(val year: Year): PieceEarningsByMonthEvent

//    data class QueryProductByMonth(val month: Int): ProductYearMonthEvent

}