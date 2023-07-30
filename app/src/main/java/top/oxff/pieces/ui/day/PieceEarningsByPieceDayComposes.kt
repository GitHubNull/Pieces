package top.oxff.pieces.ui.day

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.BubbleChart
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Storage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//@Preview
@Composable
fun PrePieceEarningsCard(){
    PieceEarningsCard(
        indexAndCnt = "[1/2]",
        price = "1.3",
        quantity = "34",
        total = "${1.3*34}",
        dateTime = "2023-07-17 12:12:00",
        onEditClick = { },
        onDeleteClick = { },
        modifier = Modifier.padding(16.dp)
    )
}


@Composable
fun PieceEarningsCard(
    indexAndCnt: String,
    price: String,
    quantity: String,
    total: String,
    dateTime: String,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(200.dp),
        contentColor = LocalContentColor.current,
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
        border = null
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = indexAndCnt,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f).padding(bottom = 8.dp)
                )
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "编辑",
                    tint = Color.Blue,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .size(24.dp)
                        .clickable {
//                            vibrator.vibrate(50)
                            onEditClick()
                        }
                )
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = "删除",
                    tint = Color.Red,
                    modifier = Modifier
                        .padding(bottom = 8.dp, start = 48.dp, end = 48.dp)
                        .size(24.dp)
                        .clickable {
//                            vibrator.vibrate(50)
                            onDeleteClick()
                        }
                )
            }
//            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.AttachMoney,
                    contentDescription = "单价",
                    tint = Color.Gray,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = price,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Storage,
                    contentDescription = "数量",
                    tint = Color.Gray,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = quantity,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.AttachMoney,
                    contentDescription = "总额",
                    tint = Color.Gray,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = total,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector= Icons.Rounded.Schedule,
                    contentDescription = "日期时间",
                    tint = Color.Gray,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = dateTime,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


//@Preview
//@Composable
//fun PreDayCard() {
////    CustomCard(amount = "123.45", day = "22", week = "六") {
////
////    }
//    DayCard(
//        index = "1",
//        cnt = "5",
//        price = "0.5",
//        quantity = "28",
//        total = "123.45",
//        dateTime = "12.34:56",
//        onEditClick = { }) {
//
//    }
//}

@Composable
fun DayCard(index: String,
            cnt: String,
            price: String,
            quantity: String,
            total: String,
            dateTime: String,
            onEditClick: () -> Unit,
            onDeleteClick: () -> Unit
            /* ,modifier: Modifier = Modifier */) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .size(180.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Row {
            Box(modifier = Modifier.weight(9f)) {
                Column {
                    Box(
                        modifier = Modifier
                            .weight(6f)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = total,
                            modifier = Modifier
                                .padding(5.dp),
                            style = TextStyle(
                                fontSize = 50.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(5f)
                            .fillMaxSize()
                            .padding(6.dp)
                    ) {
                        Column {
                            Row(modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 5.dp)) {
                                Box(modifier = Modifier.weight(1f)) {
                                    Row {
                                        Icon(
                                            imageVector = Icons.Filled.AttachMoney,
                                            contentDescription = "单价",
                                            tint = Color.Blue
                                        )
                                        Text(
                                            text = price, // 单价
                                            modifier = Modifier
                                                .padding(start = 5.dp),
                                            style = TextStyle(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.SansSerif
                                            )
                                        )
                                    }
                                }
                                Box(modifier = Modifier.weight(1f)) {
                                    Row {
                                        Icon(
                                            imageVector = Icons.Filled.BubbleChart,
                                            contentDescription = "数量",
                                            tint = Color.Blue
                                        )
                                        Text(
                                            text = quantity, //数量
                                            modifier = Modifier
                                                .padding(start = 5.dp),
                                            style = TextStyle(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.SansSerif
                                            )
                                        )
                                    }
                                }
                            }

                            Box(modifier = Modifier.weight(1f)) {
                                Row {
                                    Icon(
                                        imageVector = Icons.Filled.Schedule,
                                        contentDescription = "时间",
                                        tint = Color.Blue,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(text = dateTime, modifier = Modifier.padding(start = 5.dp))
                                }
                            }
                        }

                    }
                }
            }

            Box(modifier = Modifier.weight(3f)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .weight(4f)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Color.Black)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(4f)
                                    .fillMaxWidth()
                                    .background(Color.Green),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = index,
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontFamily = FontFamily.SansSerif
                                    ),
                                    color = Color.Black
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .weight(4f)
                                    .fillMaxWidth()
                                    .background(Color.Gray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = cnt,
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontFamily = FontFamily.SansSerif
                                    ),
                                    color = Color.Black
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(8f)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(onClick = { onEditClick() }, modifier = Modifier.weight(4f)) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "编辑",
                                    tint = Color.Blue
                                )
                            }
                            IconButton(onClick = { onDeleteClick() }, modifier = Modifier.weight(4f)) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "删除",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}