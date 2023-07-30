package top.oxff.pieces.day

import top.oxff.pieces.database.dataModel.Piece
import java.time.LocalDateTime

sealed interface PieceEarningsByDayEvent{
    object AddPieceEarningsByDay: PieceEarningsByDayEvent
    object UpdatePiece: PieceEarningsByDayEvent

    data class OnPriceChange(val price: String): PieceEarningsByDayEvent
    data class OnQuantityChange(val quantity: String): PieceEarningsByDayEvent

    data class OnDateTimeChange(val dateTime: LocalDateTime): PieceEarningsByDayEvent
    data class OnTargetDateChange(val targetDate: LocalDateTime): PieceEarningsByDayEvent

    object AddDialog: PieceEarningsByDayEvent
    object UpdateDialog: PieceEarningsByDayEvent
    object ShowDialog: PieceEarningsByDayEvent
    object HideDialog: PieceEarningsByDayEvent
//    object ShowDeleteConfirm: ProductYearMonthDayEvent

    data class DeletePieceEarningsByDay(val piece: Piece): PieceEarningsByDayEvent
    data class UpdatePieceEarningsByDay(val piece: Piece): PieceEarningsByDayEvent
    data class OnChangeDeleteConfirm(val show: Boolean): PieceEarningsByDayEvent
    data class OnChangeTargetDateProduct(val piece: Piece): PieceEarningsByDayEvent
    data class OnChangeShowDatePicker(val show: Boolean): PieceEarningsByDayEvent

    data class OnPieceIdChange(val id: Int): PieceEarningsByDayEvent

//    data class QueryPieceEarningsByDay(val targetDate: String): PieceEarningsByDayEvent

}