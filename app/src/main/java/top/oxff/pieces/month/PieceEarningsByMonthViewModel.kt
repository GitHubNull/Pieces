package top.oxff.pieces.month

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import top.oxff.pieces.database.dao.PieceDao
import top.oxff.pieces.util.getFirstAndLastDayOfMonth
import java.time.YearMonth

class PieceEarningsByMonthViewModel(private val dao: PieceDao) : ViewModel() {
//    private val _year = MutableStateFlow(Year.now())
    private val _yearMonth = MutableStateFlow(YearMonth.now())
    private val _startAndEndDate = _yearMonth.map { getFirstAndLastDayOfMonth(it) }
    @OptIn(ExperimentalCoroutinesApi::class)
    private val productsByMonth = _startAndEndDate.flatMapLatest { dateRange ->
        dao.getPieceEarningsBetweenDates(dateRange.first, dateRange.second)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _productState = MutableStateFlow(PieceEarningsByMonthState())
    val pieceEarningsByMonthState = combine(
        _productState,
//        _year,
        _yearMonth,
        _startAndEndDate,
        productsByMonth
    ) { productState, yearMonth, startAndEndDate, productsByMonth ->
        productState.copy(
//            year = year,
            yearMonth = yearMonth,
            startAndEndDate = startAndEndDate,
            salesByDay = productsByMonth
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, PieceEarningsByMonthState())

    fun onEvent(event: PieceEarningsByMonthEvent){
        when(event){
            is PieceEarningsByMonthEvent.OnChangeByMonthValue -> {
                _yearMonth.value = event.yearMonth
            }

        }
    }
}
