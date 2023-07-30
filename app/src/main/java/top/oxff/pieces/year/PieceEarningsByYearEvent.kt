package top.oxff.pieces.year

import java.time.Year

sealed class PieceEarningsByYearEvent {
    data class OnYearChanged(val year: Year) : PieceEarningsByYearEvent()
}
