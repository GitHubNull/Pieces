package top.oxff.pieces.ui.month

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.oxff.pieces.day.PieceEarningsByDayEvent
import top.oxff.pieces.month.PieceEarningsByMonthEvent
import top.oxff.pieces.month.PieceEarningsByMonthState
import top.oxff.pieces.ui.common.HeaderScreen
import java.math.BigDecimal
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun MonthScreen(
    pieceEarningsByMonthState: PieceEarningsByMonthState,
    monthOnEvent: (PieceEarningsByMonthEvent) -> Unit,
    dayOnEvent: (PieceEarningsByDayEvent) -> Unit,
    onSelectedChanged: (Int)->Unit
) {
//    val productYearMonthState by productYearMonthState.observeAsState()

    MonthScreenContent(pieceEarningsByMonthState = pieceEarningsByMonthState,
        onEvent = monthOnEvent,
        dayOnEvent = dayOnEvent,
        onSelectedChanged = onSelectedChanged
    )
}

@Composable
fun MonthScreenContent(
    pieceEarningsByMonthState: PieceEarningsByMonthState,
    onEvent: (PieceEarningsByMonthEvent) -> Unit,
    dayOnEvent: (PieceEarningsByDayEvent) -> Unit,
    onSelectedChanged: (Int)->Unit
) {

    val monthTotal = pieceEarningsByMonthState.salesByDay.fold(BigDecimal.ZERO) { acc, product ->
        acc + product.total
    }

    Column(Modifier.fillMaxSize()) {
        val formatter2 = DateTimeFormatter.ofPattern("yyyy年-MM月", Locale.CHINA)
        val formattedString = pieceEarningsByMonthState.yearMonth.format(formatter2)
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(2f)) {
            MonthScreenHeader(yearMonth = formattedString, monthTotal = monthTotal)
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(8f)) {
            MonthDaysListItems(
                pieceEarningsByMonthState = pieceEarningsByMonthState,
                dayOnEvent = dayOnEvent,
                onSelectedChanged = onSelectedChanged
            )
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            MonthBottomBar(pieceEarningsByMonthState = pieceEarningsByMonthState,
                onEvent = onEvent)
        }
    }
}

@Composable
fun MonthScreenHeader(yearMonth: String, monthTotal: BigDecimal) {
    HeaderScreen(mainTitle = monthTotal.toPlainString(), subTitle = yearMonth)
}

@Composable
fun MonthDaysListItems(
    pieceEarningsByMonthState: PieceEarningsByMonthState,
    dayOnEvent: (PieceEarningsByDayEvent) -> Unit,
    onSelectedChanged: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        pieceEarningsByMonthState.salesByDay.forEach { productByDay ->
//            item { PieceEarningsByDayCard(
//                pieceEarningsByDay = productByDay,
//                dayOnEvent = dayOnEvent,
//                onSelectedChanged = onSelectedChanged
//            ) }
            item { PieceEarningsByDayCard(
                pieceEarningsByDay = productByDay,
                dayOnEvent = dayOnEvent,
                onSelectedChanged = onSelectedChanged
            ) }
        }
    }
}

@Composable
fun MonthBottomBar(pieceEarningsByMonthState: PieceEarningsByMonthState, onEvent: (PieceEarningsByMonthEvent) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row {
            IconButton(onClick = { onEvent(PieceEarningsByMonthEvent.OnChangeByMonthValue(pieceEarningsByMonthState.yearMonth.minusMonths(1))) },
                modifier = Modifier
                    .weight(1f)
                    .size(48.dp),
                content = {
                    Icon(
                        Icons.Filled.ChevronLeft,
                        contentDescription = "往前一个月"
                    ) }
            )

            IconButton(onClick = { onEvent(PieceEarningsByMonthEvent.OnChangeByMonthValue(YearMonth.now())) },
                modifier = Modifier
                    .weight(1f)
                    .size(48.dp),
                content = {
                    Icon(
                        Icons.Filled.MyLocation,
                        contentDescription = "回到当前月"
                    ) }
            )

            IconButton(onClick = { onEvent(PieceEarningsByMonthEvent.OnChangeByMonthValue(pieceEarningsByMonthState.yearMonth.plusMonths(1))) },
                modifier = Modifier
                    .weight(1f)
                    .size(48.dp),
                content = {
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = "往后一个月"
                    ) }
            )
        }
    }
}

