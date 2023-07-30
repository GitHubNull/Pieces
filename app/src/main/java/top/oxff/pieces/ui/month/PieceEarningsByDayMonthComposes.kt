package top.oxff.pieces.ui.month

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.oxff.pieces.database.dataModel.PieceEarningsByDay
import top.oxff.pieces.day.PieceEarningsByDayEvent
import top.oxff.pieces.util.getChineseWeekday
import top.oxff.pieces.util.getDayOfMonth
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun PieceEarningsByDayCard0(
    pieceEarningsByDay: PieceEarningsByDay,
    dayOnEvent: (PieceEarningsByDayEvent) -> Unit,
    onSelectedChanged: (Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(130.dp),
        contentColor = LocalContentColor.current,
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
        border = null
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "￥${pieceEarningsByDay.total}",
                style = TextStyle(
                    color = LocalContentColor.current,
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = convertDateFormat(pieceEarningsByDay.day),
                style = TextStyle(
                    color = LocalContentColor.current,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(onClick = {
                val timeString = "00:00:00"
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val localDate = LocalDate.parse(pieceEarningsByDay.day)
                val localTime = LocalTime.parse(timeString)
                val localDateTime = LocalDateTime.of(localDate, localTime)
                dayOnEvent(PieceEarningsByDayEvent.OnTargetDateChange(localDateTime))
                onSelectedChanged(0)
            }) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "详情",
                    tint = LocalContentColor.current,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

@Composable
fun PieceEarningsByDayCard(pieceEarningsByDay: PieceEarningsByDay,
                           dayOnEvent: (PieceEarningsByDayEvent) -> Unit,
                           onSelectedChanged: (Int) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .size(180.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .weight(9f)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = pieceEarningsByDay.total.toString(),
                    modifier = Modifier
                        .padding(16.dp),
                    style = TextStyle(
                        fontSize = 54.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                )
            }
            Column(modifier = Modifier.weight(2f)) {
                Box(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.background(Color.Red)) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                            Text(
                                text = getDayOfMonth(pieceEarningsByDay.day),
                                style = TextStyle(
                                    fontSize = 28.sp,
                                    fontFamily = FontFamily.SansSerif
                                ),
                                color = Color.Black
                            )
                        }

                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                            Divider(
                                color = Color.Gray,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .height(2.dp)
                            )
                        }

                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                            Text(
                                text = getChineseWeekday(pieceEarningsByDay.day),
                                style = TextStyle(
                                    fontSize = 28.sp,
                                    fontFamily = FontFamily.SansSerif
                                ),
                                color = Color.Black
                            )
                        }
                    }
                }
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                val timeString = "00:00:00"
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                val localDate = LocalDate.parse(pieceEarningsByDay.day)
                                val localTime = LocalTime.parse(timeString)
                                val localDateTime = LocalDateTime.of(localDate, localTime)
                                dayOnEvent(PieceEarningsByDayEvent.OnTargetDateChange(localDateTime))
                                onSelectedChanged(0)
                            }
                    )
                }
            }
        }
    }
}

fun convertDateFormat(dateString: String): String {
    val date = LocalDate.parse(dateString)
    val formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 EEEE", Locale.CHINA)
    return date.format(formatter)
}