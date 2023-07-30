@file:Suppress("DEPRECATION")

package top.oxff.pieces.ui.year

import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import top.oxff.pieces.database.AppDatabase
import top.oxff.pieces.database.dataModel.Piece
import top.oxff.pieces.month.PieceEarningsByMonthEvent
import top.oxff.pieces.ui.common.HeaderScreen
import top.oxff.pieces.year.PieceEarningsByYearEvent
import top.oxff.pieces.year.PieceEarningsByYearState
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Year
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.random.Random

@Composable
fun YearView(yearState: PieceEarningsByYearState,
             yearOnEvent: (PieceEarningsByYearEvent) -> Unit,
             monthOnEvent: (PieceEarningsByMonthEvent) -> Unit,
             onSelectedChanged: (Int)->Unit) {
    YearScreenContent(yearState, yearOnEvent,
        monthOnEvent = monthOnEvent,
        onSelectedChanged = onSelectedChanged
    )

}

@Composable
fun YearScreenContent(yearState: PieceEarningsByYearState,
                      onEvent: (PieceEarningsByYearEvent) -> Unit,
                      monthOnEvent: (PieceEarningsByMonthEvent) -> Unit,
                      onSelectedChanged: (Int)->Unit) {
//        Box(Modifier.fillMaxSize()) {
//        Text(text = "年视图")
//        InsertTestDataButton()
//    }

    val yearTotal = yearState.productsByYear.fold(BigDecimal.ZERO) { acc, product ->
        acc + product.total
    }

    Column(Modifier.fillMaxSize()) {
        val formatter = DateTimeFormatter.ofPattern("yyyy年")
        val formattedString = yearState.year.format(formatter)
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(2f)) {
            YearScreenHeader(formattedString, yearTotal)
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(8f)) {
                MonthItemsScreen(
                    yearState, monthOnEvent = monthOnEvent,
                    onSelectedChanged = onSelectedChanged
                )
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            YearBottomBarScreen(yearState, onEvent)
        }
    }
}

@Composable
fun YearScreenHeader(year: String, yearTotal: BigDecimal) {
    HeaderScreen(mainTitle = yearTotal.toEngineeringString(), subTitle = year)
}

@Composable
fun MonthItemsScreen(
    yearState: PieceEarningsByYearState,
    monthOnEvent: (PieceEarningsByMonthEvent) -> Unit,
    onSelectedChanged: (Int) -> Unit
) {
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
    ) {
        yearState.productsByYear.forEach { product ->
            item { MonthCard(
                product, monthOnEvent = monthOnEvent,
                onSelectedChanged = onSelectedChanged
            ) }
        }
    }
}

@Composable
fun YearBottomBarScreen(yearState: PieceEarningsByYearState, onEvent: (PieceEarningsByYearEvent) -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
    ) {
        Row {
            IconButton(onClick = { onEvent(PieceEarningsByYearEvent.OnYearChanged(yearState.year.minusYears(1))) },
                modifier = Modifier
                    .weight(1f)
                    .size(48.dp),
                content = {
                    Icon(
                        Icons.Filled.ChevronLeft,
                        contentDescription = "往前一年"
                    ) }
            )

            IconButton(onClick = { onEvent(PieceEarningsByYearEvent.OnYearChanged(Year.now())) },
                modifier = Modifier
                    .weight(1f)
                    .size(48.dp),
                content = {
                    Icon(
                        Icons.Filled.MyLocation,
                        contentDescription = "回到当前年"
                    ) }
            )

            IconButton(onClick = { onEvent(PieceEarningsByYearEvent.OnYearChanged(yearState.year.plusYears(1))) },
                modifier = Modifier
                    .weight(1f)
                    .size(48.dp),
                content = {
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = "往后一年"
                    ) }
            )
        }
    }
}


@Composable
fun InsertTestDataButton() {
    val context = LocalContext.current

    Button(
        onClick = {
            // 创建新的协程作用域
            CoroutineScope(Dispatchers.IO).launch {
                // 创建 DAO 对象
                val dao = AppDatabase.getInstance(context).dao

                Log.d("Pieces-DEBUG", "InsertTestDataButton")

                // 计算时间范围
                val startDate = LocalDate.ofYearDay(LocalDate.now().year - 1, 1)
                val endDate = LocalDate.now()

                var index = 1

                var pieces: List<Piece> = emptyList()

                // 循环插入测试数据
                var date = startDate
                while (!date.isAfter(endDate)) {
                    Log.d("Pieces-DEBUG", "InsertTestDataButton: date: $date")

                    val random = Random.Default
                    val hours = mutableSetOf<Int>()
                    val count = random.nextInt(1, 6) // 随机生成需要取的数的个数
                    while (hours.size < count) {
                        val number = random.nextInt(8, 24)
                        if (number !in hours) {
                            hours.add(number)
                        }
                    }

                    hours.forEach { hour ->
                        // 计算日期时间和价格
                        val dateTime =
                            LocalDateTime.of(date, LocalTime.of(hour, Random.nextInt(0, 60)))
                        val price = BigDecimal.valueOf(Random.nextDouble(0.1, 1.9))
                            .setScale(2, BigDecimal.ROUND_HALF_UP)

                        // 创建产品对象并插入数据库
                        val piece = Piece(
                            price = price,
                            quantity = Random.nextInt(1, 50),
                            dateTime = dateTime
                        )
                        Log.d("Pieces-DEBUG", "InsertTestDataButton: product: $piece")
                        pieces = pieces + piece
                        index += 1
//                      sleep(50)
                    }
                    date = date.plus(1, ChronoUnit.DAYS)
                }
                try {
                    dao.insertPieces(pieces)
                } catch (e: Exception) {
                    Log.e("Pieces-DEBUG", "InsertTestDataButton: $e")
                }
            }
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Insert Test Data")
    }
}