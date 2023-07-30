package top.oxff.pieces.day

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.oxff.pieces.database.dao.PieceDao
import top.oxff.pieces.database.dataModel.Piece
import top.oxff.pieces.types.DialogType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class PieceEarningsByDayViewModel(private val dao: PieceDao) : ViewModel() {
    private val _targetDate = MutableStateFlow(LocalDateTime.now(ZoneId.of("Asia/Shanghai")))
//    val formatterDay: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // 定义格式化器常量
//    val formatterYearMonth: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM") // 定义格式化器常量
//    val formatterYear: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy") // 定义格式化器常量
    private val _deleteConfirmShow = MutableStateFlow(false)
//    private val _showDatePicker = MutableStateFlow(false)
    private val _targetDatePiece = MutableStateFlow(Piece(id = -1, price = 0.toBigDecimal(), quantity = 0, dateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"))))

    private val _dateState = MutableStateFlow(PieceEarningsByDayState())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _dateProducts = _targetDate.flatMapLatest { _targetDate ->
        dao.getPiecesByDate(_targetDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dateState = combine(_dateState, _targetDate, _dateProducts, _deleteConfirmShow, _targetDatePiece) {
            dateState, targetDate, dateProducts, deleteConfirmShow, targetDateProduct ->
        dateState.copy(
            datePieces = dateProducts,
            targetDate = targetDate,
            deleteConfirmShow = deleteConfirmShow,
            targetDatePiece = targetDateProduct
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, PieceEarningsByDayState())

    fun onEvent(event: PieceEarningsByDayEvent){
        when(event){
            is PieceEarningsByDayEvent.AddPieceEarningsByDay -> {
                val priceStr = dateState.value.price
                val quantityStr = dateState.value.quantity
                val dateTime = dateState.value.dateTime

                if (priceStr.isBlank() || quantityStr.isBlank() ) return

                val price = priceStr.toBigDecimal()
                val quantity = quantityStr.toIntOrNull() ?: return

                val piece = Piece(
                    price = price,
                    quantity = quantity,
                    dateTime = dateTime
                )

                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        dao.insertPiece(piece)
                    }
                }

                _dateState.update { it.copy(
                    price = "",
                    quantity = "",
                    dateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"))
                ) }
            }
            is PieceEarningsByDayEvent.UpdatePiece -> {
                val priceStr = dateState.value.price
                val quantityStr = dateState.value.quantity
                val dateTime = dateState.value.dateTime

                if (priceStr.isBlank() || quantityStr.isBlank() ) return

                val price = priceStr.toBigDecimal()
                val quantity = quantityStr.toIntOrNull() ?: return

                val piece = Piece(
                    id = dateState.value.id,
                    price = price,
                    quantity = quantity,
                    dateTime = dateTime
                )

                viewModelScope.launch {
//                    Log.d("PieceEarningsByDayViewModel", "updatePiece: ${piece.id}")
                    withContext(Dispatchers.IO) {
                        dao.updatePiece(piece.id, piece.price, piece.quantity, piece.dateTime)
                    }
                }

                _dateState.update { it.copy(
                    id = -1,
                    price = "",
                    quantity = "",
                    dateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"))
                ) }

            }
            is PieceEarningsByDayEvent.DeletePieceEarningsByDay -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        dao.deletePiece(event.piece)
                    }
                }
            }
            is PieceEarningsByDayEvent.OnPieceIdChange -> {
                _dateState.update { it.copy(id = event.id) }
            }

            is PieceEarningsByDayEvent.UpdatePieceEarningsByDay -> {
                if (event.piece.price == BigDecimal("0.0") || event.piece.quantity == 0) return

                viewModelScope.launch {
                    dao.updatePiece(event.piece.id, event.piece.price, event.piece.quantity, event.piece.dateTime)
                }
            }
            is PieceEarningsByDayEvent.OnPriceChange -> {
                _dateState.update { it.copy(price = event.price) }
            }
            is PieceEarningsByDayEvent.OnQuantityChange -> {
                _dateState.update { it.copy(quantity = event.quantity) }
            }
            is PieceEarningsByDayEvent.OnDateTimeChange -> {
                _dateState.update {
                    it.copy(dateTime = event.dateTime)
                }
            }
            is PieceEarningsByDayEvent.OnTargetDateChange -> {
                _targetDate.value = event.targetDate
            }
            is PieceEarningsByDayEvent.AddDialog -> {
                _dateState.update { it.copy(
                    dialogTitle = "添加",
                    dialogType = DialogType.ADD
                ) }
            }
            is PieceEarningsByDayEvent.UpdateDialog -> {
                _dateState.update { it.copy(
                    dialogTitle = "修改",
                    dialogType = DialogType.UPDATE
                ) }
            }
            is PieceEarningsByDayEvent.ShowDialog -> {
                _dateState.update { it.copy(
                    dialogTitle = "展示",
                    dialogType = DialogType.SHOW
                ) }
            }
            is PieceEarningsByDayEvent.HideDialog -> {
                _dateState.update { it.copy(
                    dialogTitle = "",
                    dialogType = DialogType.HIDE
                ) }
            }

            is PieceEarningsByDayEvent.OnChangeShowDatePicker -> {
                _dateState.update { it.copy(
                    showDatePicker = event.show
                ) }
            }

            is PieceEarningsByDayEvent.OnChangeDeleteConfirm -> {
                _deleteConfirmShow.value = event.show
            }
            is PieceEarningsByDayEvent.OnChangeTargetDateProduct -> {
                _targetDatePiece.value = event.piece
            }
        }
    }

}