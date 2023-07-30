package top.oxff.pieces.year

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
import top.oxff.pieces.util.getFirstAndLastDayOfYear
import java.time.Year

class PieceEarningsByYearViewModel(private val dao: PieceDao) : ViewModel() {
    private val _year = MutableStateFlow(Year.now())
    private val startAndEndDate = _year.map { getFirstAndLastDayOfYear(it) }
    private val _pieceEarningsByYearState = MutableStateFlow(PieceEarningsByYearState())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _productsByYear = startAndEndDate.flatMapLatest { dateRange ->
        dao.getSalesBetweenDatesByMonth(dateRange.first, dateRange.second)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pieceEarningsByYearState = combine(_pieceEarningsByYearState, _year, startAndEndDate, _productsByYear) {
            productYearState, year, startAndEndDate, productsByYear ->
        productYearState.copy(
            year = year,
            startAndEndDate = startAndEndDate,
            productsByYear = productsByYear
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, PieceEarningsByYearState())


    fun onEvent(event: PieceEarningsByYearEvent){
        when(event){
            is PieceEarningsByYearEvent.OnYearChanged -> {
                _year.value = event.year
            }

        }
    }
}