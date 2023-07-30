package top.oxff.pieces.day

import top.oxff.pieces.database.dataModel.Piece
import top.oxff.pieces.types.DialogType
import java.time.LocalDateTime
import java.time.ZoneId

data class PieceEarningsByDayState(
    val targetDate: LocalDateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai")),
    val datePieces: List<Piece> = emptyList(),
    val targetDatePiece: Piece = Piece(id = -1, price = 0.toBigDecimal(), quantity = 0, dateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"))),

    val showDatePicker: Boolean = false,
    val id: Int = -1,
    val price: String = "",
    val quantity: String = "",
    val dateTime: LocalDateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai")),

    val dialogTitle: String = "",
    val dialogType: DialogType = DialogType.HIDE,
    val deleteConfirmShow: Boolean = false
)