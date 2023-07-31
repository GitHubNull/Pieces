@file:Suppress("UNUSED_EXPRESSION")

package top.oxff.pieces.ui.day

//import android.icu.math.BigDecimal
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import top.oxff.pieces.database.dataModel.Piece
import top.oxff.pieces.day.PieceEarningsByDayEvent
import top.oxff.pieces.day.PieceEarningsByDayState
import top.oxff.pieces.types.DialogType
import top.oxff.pieces.ui.common.HeaderScreen
import top.oxff.pieces.ui.common.ProductDialog
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun DayScreen(dateState: PieceEarningsByDayState, dayOnEvent: (PieceEarningsByDayEvent) -> Unit) {
//    val formatterDay: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    Column(Modifier.fillMaxWidth()) {

        if (dateState.dialogType != DialogType.HIDE) {
            ProductDialog(state = dateState, onEvent = dayOnEvent)
        }

        val todayPieces: List<Piece> = dateState.datePieces

        var totalMoney: BigDecimal = BigDecimal.ZERO
        for (todayProduct in todayPieces) {
            totalMoney =
                totalMoney.add(todayProduct.price.multiply(BigDecimal(todayProduct.quantity)))
        }


        if (totalMoney > BigDecimal.ZERO) totalMoney.toEngineeringString() else "0.00"
//        val formatDate = dateState.targetDate.format(formatterDay)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
        )
        {
            DayHeaderScreen(dayEarnings = totalMoney, dateTime = dateState.targetDate)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(7f)
        )
        {
            DayListItems(dateState = dateState, onEvent = dayOnEvent)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        )
        {
            DayBottomBar(dateState = dateState, onEvent = dayOnEvent)
        }


    }
}


@Composable
fun DayHeaderScreen(dayEarnings: BigDecimal, dateTime: LocalDateTime) {
    HeaderScreen(
        mainTitle = dayEarnings.toPlainString(),
        subTitle = dateTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))
    )
}

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DayListItems(dateState: PieceEarningsByDayState, onEvent: (PieceEarningsByDayEvent) -> Unit) {

    // 列表内容
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        val cnt = dateState.datePieces.size
        var index = 0
        for (todayProduct in dateState.datePieces) {
            index += 1
            val constantIndex = index
            item {
//                val indexAndCnt = "[${constantIndex}/${cnt}]"
                val price = todayProduct.price.toPlainString()
                val quantity = todayProduct.quantity.toString()
                val total =
                    todayProduct.price.multiply(BigDecimal(todayProduct.quantity)).toPlainString()
                val dateTime = todayProduct.dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))

                DayCard(index = constantIndex.toString(), cnt = cnt.toString(), price = price,
                    quantity = quantity, total = total, dateTime = dateTime,
                    onEditClick = {
                        onEvent(PieceEarningsByDayEvent.OnChangeTargetDateProduct(todayProduct))
                        onEvent(PieceEarningsByDayEvent.OnPieceIdChange(todayProduct.id))
                        onEvent(PieceEarningsByDayEvent.OnPriceChange(todayProduct.price.toPlainString()))
                        onEvent(PieceEarningsByDayEvent.OnQuantityChange(todayProduct.quantity.toString()))
                        onEvent(PieceEarningsByDayEvent.OnDateTimeChange(todayProduct.dateTime))
                        onEvent(PieceEarningsByDayEvent.UpdateDialog)
                    },
                    onDeleteClick = {
                        onEvent(PieceEarningsByDayEvent.OnChangeDeleteConfirm(true))
                        onEvent(PieceEarningsByDayEvent.OnChangeTargetDateProduct(todayProduct))
                    })
            }
        }





//        item {
//
//        }
    }


//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .padding(8.dp)){
//        // 悬浮按钮
//        FloatingActionButton(
//            onClick = { /* 悬浮按钮点击事件处理 */ },
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(16.dp)
//        ) {
//            Icon(Icons.Filled.Add, contentDescription = "添加")
//        }
//    }

    if (dateState.deleteConfirmShow) {
        DeleteItemDialog(
            onConfirm = {
                onEvent(PieceEarningsByDayEvent.DeletePieceEarningsByDay(dateState.targetDatePiece))
                onEvent(PieceEarningsByDayEvent.OnChangeDeleteConfirm(false))
            },
            onCancel = { onEvent(PieceEarningsByDayEvent.OnChangeDeleteConfirm(false)) })
    }
}

@Composable
fun DayBottomBar(
    dateState: PieceEarningsByDayState,
    onEvent: (PieceEarningsByDayEvent) -> Unit
) {
    Column {
        Box(Modifier
            .weight(1f)
            .fillMaxWidth()
            .padding(5.dp)){
            IconButton(
                onClick = { onEvent(PieceEarningsByDayEvent.AddDialog) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(5.dp)
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "添加",
                    tint = Color.Green,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
        Row(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            IconButton(onClick = {
                onEvent(
                    PieceEarningsByDayEvent.OnTargetDateChange(
                        dateState.targetDate.minusDays(
                            1
                        )
                    )
                )
            },
                modifier = Modifier
                    .padding(2.dp)
                    .weight(1f),
                content = {
                    Icon(
                        Icons.Filled.ChevronLeft,
                        contentDescription = "往前一天"
                    )

                })
            IconButton(onClick = {
                onEvent(PieceEarningsByDayEvent.OnTargetDateChange(LocalDateTime.now()))
            },
                modifier = Modifier
                    .padding(2.dp)
                    .weight(1f),
                content = {
                    Icon(
                        Icons.Filled.MyLocation,
                        contentDescription = "回到当前日期"
                    )

                })
            IconButton(onClick = {
                onEvent(
                    PieceEarningsByDayEvent.OnTargetDateChange(
                        dateState.targetDate.plusDays(
                            1
                        )
                    )
                )
            },
                modifier = Modifier
                    .padding(2.dp)
                    .weight(1f),
                content = {
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = "往后一天"
                    )

                })
        }
    }
}

@Composable
fun DeleteItemDialog(onConfirm: () -> Unit, onCancel: () -> Unit) {
    // 定义一个布尔型状态变量，用于控制弹窗的显示和隐藏
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("确认删除？") },
            text = { Text("你确定要删除这个项目吗？") },
            confirmButton = {
                Button(onClick = {
                    // 点击确认按钮时执行的操作
                    onConfirm()
                    showDialog.value = false
                }) {
                    Icon(Icons.Default.Check, contentDescription = "确认")
                    Text("确认")
                }
            },
            dismissButton = {
                Button(onClick = {
                    // 点击取消按钮时执行的操作
                    onCancel()
                    showDialog.value = false
                }) {
                    Icon(Icons.Default.Close, contentDescription = "取消")
                    Text("取消")
                }
            }
        )
    }
}